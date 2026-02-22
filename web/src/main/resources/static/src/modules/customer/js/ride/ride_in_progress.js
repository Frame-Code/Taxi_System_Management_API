import { showSuccessToast, showErrorToast } from "../../../../shared/components/ui_messages.js";
import { setPickupMarkerOrigin, setPickupMarkerDestiny, initializeMap, drawRouteWrapper } from "../api/external/map.js";
import { save, Keys, get } from "../../../../app/cache/localstorage.js"

const li_name = document.getElementById("li_names");
const li_last_name = document.getElementById("li_last_names");
const li_email = document.getElementById("li_email");
const li_age = document.getElementById("li_age");
const li_price = document.getElementById("li_price");
const li_payment_method = document.getElementById("li_payment_method");
const li_distance = document.getElementById("li_distance");
const li_duration = document.getElementById("li_duration");
const li_vh_color = document.getElementById("li_vh_color");
const li_vh_model = document.getElementById("li_vh_model");
const li_vh_license_plate = document.getElementById("li_vh_license_plate");
const li_vh_brand = document.getElementById("li_vh_brand");


function initInfoMap(currentRide) {
    setPickupMarkerOrigin(currentRide.infoOrigin.latOrigin, currentRide.infoOrigin.lngOrigin, "Tu lugar de recogida");
    setPickupMarkerDestiny(currentRide.infoDestiny.latDestiny, currentRide.infoDestiny.lngDestiny, "Tu destino");
    drawRouteWrapper();
}

function initInfoRide(currentRide) {
    li_name.innerHTML = `<strong>Nombre:</strong><br/>${currentRide.driver.names}`;
    li_last_name.innerHTML = `<strong>Apellido:</strong><br/>${currentRide.driver.lastNames}`;
    li_email.innerHTML = `<strong>Email:</strong><br/>${currentRide.driver.email}`;
    li_age.innerHTML = `<strong>Edad:</strong><br/>${currentRide.driver.age}`;

    li_vh_color.innerHTML = `<strong>Color:</strong><br/>${currentRide.cab.color}`;
    li_vh_model.innerHTML = `<strong>Modelo:</strong><br/>${currentRide.cab.model}`;
    li_vh_license_plate.innerHTML = `<strong>Matrícula:</strong><br/>${currentRide.cab.licensePlate}`;
    li_vh_brand.innerHTML = `<strong>Marca:</strong><br/>${currentRide.cab.brand}`;

    li_price.innerHTML = `<br/><strong>$${currentRide.price}</strong>`;
    li_distance.innerHTML = `<strong>Distancia aprox.:</strong><br/>${currentRide.distance}`;
    li_duration.innerHTML = `<strong>Minutos aprox.:</strong><br/>${Math.round(currentRide.minutes)} min`;
    li_payment_method.innerHTML = `<strong>Método de pago:</strong><br/>${currentRide.paymentMethod}`;
}

function init() {
    const currentRide = get(Keys.CurrentRide);
    showSuccessToast("¡Disfruta tu viaje!");
    initializeMap(null, null);
    
    try {
        initInfoMap(currentRide);
        initInfoRide(currentRide);

    } catch (error) {
        showErrorToast("Imposible mostrar el viaje en curso si no se ha especificado el origen y destino, vuelva a intentar")
        setTimeout(() => window.location.replace("/src/modules/customer/views/index.html"), 3000);
        return;
    }
}

init();



