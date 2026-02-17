import { API_URL, ENDPOINTS } from '../../../../config/api.config.js';
import { handleApiError } from '../../../../common/error_handler.js';
import { showSuccessToast, showInfoToast } from '../../../../common/ui_messages.js';
import { setButtonLoading } from "../../../../common/loading_button.js";
import { searchCab } from "./cab_service.js";

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
const acceptRideModal = new bootstrap.Modal(document.getElementById('acceptRideModal'));

export async function handle_cab_search(latOrigin, lngOrigin, latDestiny, lngDestiny, btnSearchCab) {
    setButtonLoading(btnSearchCab, true);
    let response = await searchCab(latOrigin, lngOrigin);
    if(!response) {
        setButtonLoading(btnSearchCab, false);
        return;
    }

    await showModal(latOrigin, lngOrigin, latDestiny, lngDestiny, response.response);
    setButtonLoading(btnSearchCab, false);
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

    li_price.innerHTML = `<br/><strong>$${rideInfo.response.totalPrice}</strong>`;
    li_distance.innerHTML = `<strong>Distancia aprox.:</strong><br/>${Math.round(rideInfo.response.distanceInfoDTO.approxDistance)} km`;
    li_duration.innerHTML = `<strong>Minutos aprox.:</strong><br/>${Math.round(rideInfo.response.distanceInfoDTO.approxMinutes)} min`;
    showSuccessToast("Información de la ruta obtenida correctamente.");
    acceptRideModal.show();
}

async function getInfoRide(latOrigin, lngOrigin, latDestiny, lngDestiny) {
    const url = `${API_URL}${ENDPOINTS.INFO_RIDE}`;
    const payload = {
        origin: {
            latitude: latOrigin,
            longitude: lngOrigin
        },
        destiny: {
            latitude: latDestiny,
            longitude: lngDestiny
        }
    }

    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    });

    if(!response.ok) {
        await handleApiError(response);
        return null;
    }

    return await response.json();
}

