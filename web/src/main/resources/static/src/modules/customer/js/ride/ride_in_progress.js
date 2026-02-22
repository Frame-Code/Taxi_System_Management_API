import { showSuccessToast } from "../../../../shared/components/ui_messages.js";
import { setPickupMarkerOrigin, initializeMap } from "../api/external/map.js";
import { save, Keys } from "../../../../app/cache/localstorage.js"

function addPickupMarkers() {
    let currentRide = JSON.parse(localStorage.getItem(Keys.CurrentRide));
    if(currentRide && currentRide.infoDestiny) {
        setPickupMarkerOrigin(currentRide.infoDestiny.latDestiny, currentRide.infoDestiny.lngDestiny);
    }
}

function init() {
    showSuccessToast("¡Disfruta tu viaje!");
    initializeMap(null, null);
}

init();

