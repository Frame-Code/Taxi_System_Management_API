import { showErrorToast, showInfoToast,showSuccessToast } from "../../../../shared/components/ui_messages.js";
import { setButtonLoading } from "../../../../shared/components/loading_button.js";
import { searchCab } from "../api/internal/cab_service.js";
import { getInfoRide } from "../api/internal/ride_service.js";
import { save, Keys, get } from "../../../../app/cache/localstorage.js"
import { SavePayment, PaymentMethod } from "../payment/payment.js"
import { setStatusCab, StatusCab } from "./index_cab.js";
import { createRide } from "../api/internal/ride_service.js";

const acceptRideModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('acceptRideModal'));
const paymentMethodModal = bootstrap.Modal.getOrCreateInstance(document.getElementById('paymentMethodModal'));
const pickupLatOrigin = document.getElementById("pickupLatOrigin");
const pickupLngOrigin = document.getElementById("pickupLngOrigin");
const pickupLatDestiny = document.getElementById("pickupLatDestiny");
const pickupLngDestiny = document.getElementById("pickupLngDestiny");
const li_name = document.getElementById("li_names");
const li_last_name = document.getElementById("li_last_names");
const li_email = document.getElementById("li_email");
const li_age = document.getElementById("li_age");
const li_price = document.getElementById("li_price");
const li_distance = document.getElementById("li_distance");
const li_duration = document.getElementById("li_duration");
const li_vh_color = document.getElementById("li_vh_color");
const li_vh_model = document.getElementById("li_vh_model");
const li_vh_license_plate = document.getElementById("li_vh_license_plate");
const li_vh_brand = document.getElementById("li_vh_brand");
const btnSearchCab = document.getElementById("btn_search_cab");

export async function handle_cab_search(latOrigin, lngOrigin, latDestiny, lngDestiny, btnSearchCab) {
    setButtonLoading(btnSearchCab, true);
    let response = await searchCab(latOrigin, lngOrigin);
    if(!response) {
        setButtonLoading(btnSearchCab, false);
        return;
    }
    setButtonLoading(btnSearchCab, false);
    await showModal(latOrigin, lngOrigin, latDestiny, lngDestiny, response.response);
}

async function showModal(latOrigin, lngOrigin, latDestiny, lngDestiny, cabInformation) {
    const rideInfo =  await getInfoRide(latOrigin, lngOrigin, latDestiny, lngDestiny);
    if(!rideInfo) {
        showInfoToast("No se pudo obtener la información de la ruta. Intenta mas tarde");
        return;
    }

    li_name.innerHTML = `<strong>Nombre:</strong><br/>${cabInformation.driverDTO.userDTO.names}`;
    li_last_name.innerHTML = `<strong>Apellido:</strong><br/>${cabInformation.driverDTO.userDTO.lastNames}`;
    li_email.innerHTML = `<strong>Email:</strong><br/>${cabInformation.driverDTO.userDTO.email}`;
    li_age.innerHTML = `<strong>Edad:</strong><br/>${cabInformation.driverDTO.userDTO.age}`;

    li_vh_color.innerHTML = `<strong>Color:</strong><br/>${cabInformation.vehicleDTO.color}`;
    li_vh_model.innerHTML = `<strong>Modelo:</strong><br/>${cabInformation.vehicleDTO.model}`;
    li_vh_license_plate.innerHTML = `<strong>Matrícula:</strong><br/>${cabInformation.vehicleDTO.licensePlate}`;
    li_vh_brand.innerHTML = `<strong>Marca:</strong><br/>${cabInformation.vehicleDTO.brand}`;

    li_price.innerHTML = `<strong>Precio total:</strong><br/><div class="fs-4 fw-bold text-success ms-4">${rideInfo.response.totalPrice}$</div>`;
    li_distance.innerHTML = `<strong>Distancia aprox.:</strong><br/>${Math.round(rideInfo.response.distanceInfoDTO.approxDistance)} km`;
    li_duration.innerHTML = `<strong>Minutos aprox.:</strong><br/>${Math.round(rideInfo.response.distanceInfoDTO.approxMinutes)} min`;
    showSuccessToast("Información de la ruta obtenida correctamente.");

    //Cache
    let currentRide = get(Keys.CurrentRide);
    if(!currentRide) {
        showErrorToast("Imposible crear un nuevo viaje si no ha especificado el origen y destino, vuelva a intentar")
        setTimeout(() => location.reload(), 3000);
        return;
    }
    currentRide.id_cab = cabInformation.id;
    currentRide.driver = {
        names: cabInformation.driverDTO.userDTO.names,
        lastNames: cabInformation.driverDTO.userDTO.lastNames,
        email: cabInformation.driverDTO.userDTO.email,
        age: cabInformation.driverDTO.userDTO.age
    };
    currentRide.cab = {
        color: cabInformation.vehicleDTO.color,
        model: cabInformation.vehicleDTO.model,
        licensePlate: cabInformation.vehicleDTO.licensePlate,
        brand: cabInformation.vehicleDTO.brand
    };
    currentRide.price = rideInfo.response.totalPrice;
    currentRide.distance = `${Math.round(rideInfo.response.distanceInfoDTO.approxDistance)} km`;
    currentRide.minutes = Math.round(rideInfo.response.distanceInfoDTO.approxMinutes);
    
    save(Keys.CurrentRide, currentRide, currentRide.minutes + 30);
    acceptRideModal.show();
}

export async function startRideHandler() {
    let currentRide = get(Keys.CurrentRide);
    if(!currentRide) {
        showErrorToast("Imposible iniciar el viaje si no se ha creado un viaje, vuelva a intentar")
        setTimeout(() => location.reload(), 3000);
        return;
    }

    const response = await SavePayment(currentRide.paymentMethod, currentRide.price);
    if(!response) {
        showErrorToast("No se pudo guardar el método de pago, intente nuevamente.");
        return;
    }

    //Cambiar estado del taxi a Working
    await setStatusCab(StatusCab.Working);  
    const rideCreated = await createRide({
        coordinatesDTO: {
            origin: {
                latitude: currentRide.infoOrigin.latOrigin,
                longitude: currentRide.infoOrigin.lngOrigin
            },
            destiny: {
                latitude: currentRide.infoDestiny.latDestiny,
                longitude: currentRide.infoDestiny.lngDestiny
            }
        },
        idTaxi: currentRide.id_cab,
        idPayment: response.id,
        idCityOrigin: currentRide.infoOrigin.idCityOrigin,
        idCityDestiny: currentRide.infoDestiny.idCityDestiny
    });

    if(!rideCreated) {
        showErrorToast("No se pudo crear el viaje, intente nuevamente.");
        return;
    }

    window.location.replace("/src/modules/customer/views/ride_in_progress.html");
}

export function acceptRideHandler() {
    let currentRide = get(Keys.CurrentRide);
    if(!currentRide) {
        showErrorToast("Imposible crear un nuevo viaje si no se ha asignado un conductor al viaje, vuelva a intentar")
        setTimeout(() => location.reload(), 3000);
    }
    currentRide.paymentMethod = PaymentMethod.Cash;
    save(Keys.CurrentRide, currentRide, currentRide.minutes);
    acceptRideModal.hide();
    paymentMethodModal.show();
}

export async function searchCabHandler() {
    if(!pickupLatOrigin.value || !pickupLngOrigin.value || !pickupLatDestiny.value || !pickupLngDestiny.value) {
        showErrorToast("Por favor, ingresa tanto el lugar de recogida como el destino.");
        return;
    }

    await handle_cab_search(pickupLatOrigin.value, pickupLngOrigin.value, pickupLatDestiny.value, pickupLngDestiny.value, btnSearchCab);
}

