import { setPickupMarkerOrigin, setPickupMarkerDestiny, clearPickupMarkerOrigin, clearPickupMarkerDestiny, initializeMap } from "./services/map.js";
import { get_location_name } from "./api/client.js";
import { setButtonLoading } from "../../common/loading_button.js";
import { showErrorToast } from "../../common/ui_messages.js";
import { attachAutocomplete } from "./suggestions_address.js";

const impOrigin = document.getElementById("imp_origen");
const pickupActions = document.getElementById("pickupActions");
const btnUseCurrentLocation = document.getElementById("btnUseCurrentLocation");
const pickupStatus = document.getElementById("pickupStatus");
const pickupLatOrigin = document.getElementById("pickupLatOrigin");
const pickupLngOrigin = document.getElementById("pickupLngOrigin");

const impDestiny = document.getElementById("imp_destino");
const pickupLatDestiny = document.getElementById("pickupLatDestiny");
const pickupLngDestiny = document.getElementById("pickupLngDestiny");

function showActions(e){ 
    pickupActions.classList.remove("d-none");
}

function hideActions(e){
    const clickedInside = pickupActions.contains(e.target) || impOrigin.contains(e.target);
    if (!clickedInside) {
        pickupActions.classList.add("d-none");
        pickupStatus.classList.add("d-none");
    }
}

function setStatus(msg) {
    pickupStatus.textContent = msg;
    pickupStatus.classList.remove("d-none");
}

// Geolocation functions
async function getCoordinatesSuccess(pos, watchId, e) {
    const { latitude, longitude, accuracy } = pos.coords;

    setButtonLoading(btnUseCurrentLocation, true); 
    let locationName = await get_location_name(latitude, longitude);
    setButtonLoading(btnUseCurrentLocation, false);
    navigator.geolocation.clearWatch(watchId);
    watchId = null;

    if(!locationName) {
        setStatus("No se pudo obtener el nombre de la ubicación.");
        return watchId;
    }

    impOrigin.value = locationName;
    pickupLatOrigin.value = latitude;
    pickupLngOrigin.value = longitude;
    setPickupMarkerOrigin(latitude, longitude, "Ubicación actual");
    setStatus("Ubicación detectada correctamente.");
    hideActions(e);
    return watchId;
}

function getCoordinatesError(error, e) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            setStatus("Permiso denegado para acceder a la ubicación.");
            showErrorToast("Permiso denegado para acceder a la ubicación.");
            break;
        case error.POSITION_UNAVAILABLE:
            setStatus("La información de ubicación no está disponible.");
            showErrorToast("La información de ubicación no está disponible.");
            break;
        case error.TIMEOUT:
            setStatus("La solicitud para obtener la ubicación ha caducado.");
            showErrorToast("La solicitud para obtener la ubicación ha caducado.");
            break;
        default:
            setStatus("Error desconocido al obtener la ubicación.");
            showErrorToast("Error desconocido al obtener la ubicación.");
            break;
    }
    watchId = null;
    return watchId;
}

async function getCoordinates(watchId, e) {
    if (!("geolocation" in navigator)) {
        setStatus("Tu navegador no soporta geolocalización.");
        showErrorToast("Tu navegador no soporta geolocalización.");
        return;
    }

    if(watchId !== null) 
        return;

    setStatus("Solicitando permiso y obteniendo ubicación...");
    
    watchId = navigator.geolocation.watchPosition(async (position) => {
        watchId = await getCoordinatesSuccess(position, watchId, e);
    }, (error) => {
        watchId = getCoordinatesError(error, e);
    }, { 
        enableHighAccuracy: true, 
        maximumAge: 0, 
        timeout: 15000 
    });
    return watchId
}

async function setDestiny(ev, lat, lng) {
    const destinySelected = document.getElementById("btn_select_destination");
    if(destinySelected) 
        setButtonLoading(destinySelected, true); 

    let locationName = await get_location_name(lat, lng);

    if(destinySelected) 
        setButtonLoading(destinySelected, false);

    if(!locationName) {
        return null;
    }

    impDestiny.value = locationName;
    pickupLatDestiny.value = lat;
    pickupLngDestiny.value = lng;
    return locationName;
}

async function setOrigin(ev, lat, lng) {
    const originSelected = document.getElementById("btn_select_origin");
    if(originSelected) 
        setButtonLoading(originSelected, true);  

    let locationName = await get_location_name(lat, lng);
    
    if(originSelected) 
        setButtonLoading(originSelected, false);

    if(!locationName) {
        return null;
    }

    impOrigin.value = locationName;
    pickupLatOrigin.value = lat;
    pickupLngOrigin.value = lng;
    return locationName;
}


function init(){
    initializeMap(setDestiny, setOrigin);

    let watchId = null;
    //Listeners--------------------------------------------
    impOrigin.addEventListener("click", showActions);
    document.addEventListener("click", hideActions);
    btnUseCurrentLocation.addEventListener("click", async (e) => {
        watchId = await getCoordinates(watchId, e);
    });

    // Initialize autocomplete for pickup and destination inputs
    attachAutocomplete({
        inputEl: impOrigin,
        sugEl: document.getElementById("pickupSug"),
        onSelected: async ({ displayName, lat, lng, raw }) => {
            let origin = await setOrigin(null, lat, lng);
            if(!origin)  {
                pickupLatOrigin.value = null;
                pickupLngOrigin.value = null;
                impOrigin.value = "";
                clearPickupMarkerOrigin();
                return;
            }

            pickupLatOrigin.value = lat;
            pickupLngOrigin.value = lng;
            impOrigin.value = displayName;
            setPickupMarkerOrigin(lat, lng, "Tu lugar de recogida");
        }
    });

    attachAutocomplete({
        inputEl: impDestiny,
        sugEl: document.getElementById("dropoffSug"),
        onSelected: async ({ displayName, lat, lng, raw }) => {
            let destiny = await setDestiny(null, lat, lng);
            if(!destiny) {
                pickupLatDestiny.value = null;
                pickupLngDestiny.value = null;
                impDestiny.value = "";    
                clearPickupMarkerDestiny();
                return;
            }

            pickupLatDestiny.value = lat;
            pickupLngDestiny.value = lng;
            impDestiny.value = displayName;
            setPickupMarkerDestiny(lat, lng, "Tu destino");
            
        }
    }); 
}

//--- Main ---
init();
