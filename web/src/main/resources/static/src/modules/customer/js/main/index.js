import { setPickupMarkerOrigin, initializeMap } from "../api/external/map.js";
import { showActions, hideActions, setStatus, getJsonAutoComplete } from "../ride/pickup_actions.js";
import { getCoordinates } from "../api/navigator/geolocation.js";
import { acceptRideHandler, searchCabHandler, startRideHandler } from "../ride/ride.js";
import { get_location_name } from "../api/internal/location_service.js";
import { setButtonLoading } from "../../../../shared/components/loading_button.js";
import { showErrorToast } from "../../../../shared/components/ui_messages.js";
import { attachAutocomplete } from "../suggestions/suggestions_service.js";
import { InitPaymentMethod } from "../payment/payment.js";

const impOrigin = document.getElementById("imp_origen");
const btnUseCurrentLocation = document.getElementById("btnUseCurrentLocation");
const pickupLatOrigin = document.getElementById("pickupLatOrigin");
const pickupLngOrigin = document.getElementById("pickupLngOrigin");

const impDestiny = document.getElementById("imp_destino");
const pickupLatDestiny = document.getElementById("pickupLatDestiny");
const pickupLngDestiny = document.getElementById("pickupLngDestiny");

const btnSearchCab = document.getElementById("btn_search_cab");
const btnAcceptRide = document.getElementById("btn_accept_ride");
const btnStartRide = document.getElementById("btn_start_ride");

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
    
    //Cache
    let currentRide = get(Keys.CurrentRide);
    if(!currentRide) {
        currentRide = {
            infoDestiny: {
                idCityDestiny: locationName.idCity,
                latDestiny: lat,
                lngDestiny: lng
            }
        };
    } else {
        currentRide.infoDestiny = {
            idCityDestiny: locationName.idCity,
            latDestiny: lat,
            lngDestiny: lng
        }
    }
    save(Keys.CurrentRide, JSON.stringify(currentRide), 10);

    impDestiny.value = locationName.locationString;
    pickupLatDestiny.value = lat;
    pickupLngDestiny.value = lng;
    return locationName.locationString;
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

    //Cache
    let currentRide = get(Keys.CurrentRide);
    if(!currentRide) {
        currentRide = {
            infoOrigin: {
                idCityOrigin: locationName.idCity,
                latOrigin: lat,
                lngOrigin: lng
            }
        };
    } else {
        currentRide.infoOrigin = {
            idCityOrigin: locationName.idCity,
            latOrigin: lat,
            lngOrigin: lng
        }
        
    }
    save(Keys.CurrentRide, JSON.stringify(currentRide), 10);

    impOrigin.value = locationName.locationString;
    pickupLatOrigin.value = lat;
    pickupLngOrigin.value = lng;
    return locationName.locationString;
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

    //Cache
    let currentRide = get(Keys.CurrentRide);
    if(!currentRide) {
        currentRide = {
            infoOrigin: {
                idCityOrigin: locationName.idCity,
                latOrigin: lat,
                lngOrigin: lng
            }
        };
    } else {
        currentRide.infoOrigin = {
            idCityOrigin: locationName.idCity,
            latOrigin: lat,
            lngOrigin: lng
        }
        
    }
    save(Keys.CurrentRide, JSON.stringify(currentRide), 10);

    impOrigin.value = locationName.locationString;
    pickupLatOrigin.value = latitude;
    pickupLngOrigin.value = longitude;
    setPickupMarkerOrigin(latitude, longitude, "Ubicación actual");
    setStatus("Ubicación detectada correctamente.");
    hideActions(e);
    return watchId;
}


function init(){
    initializeMap(setDestiny, setOrigin);
    InitPaymentMethod();

    let watchId = null;
    //Listeners--------------------------------------------
    impOrigin.addEventListener("click", showActions);
    document.addEventListener("click", hideActions);
    btnSearchCab.addEventListener("click", searchCabHandler);
    btnAcceptRide.addEventListener("click", acceptRideHandler);
    btnStartRide.addEventListener("click", startRideHandler);
    btnUseCurrentLocation.addEventListener("click", async (e) => {
        watchId = await getCoordinates(watchId, e, setStatus, showErrorToast, getCoordinatesSuccess);
    });

    // Initialize autocomplete for pickup and destination inputs
    attachAutocomplete(getJsonAutoComplete(true, setOrigin));
    attachAutocomplete(getJsonAutoComplete(false, setDestiny)); 
}

//--- Main ---
init();