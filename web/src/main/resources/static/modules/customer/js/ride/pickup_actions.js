import { setPickupMarkerOrigin, setPickupMarkerDestiny, clearPickupMarkerDestiny, clearPickupMarkerOrigin } from "../api/external/map.js";

const pickupActions = document.getElementById("pickupActions");
const impOrigin = document.getElementById("imp_origen");
const impDestiny = document.getElementById("imp_destino");
const pickupStatus = document.getElementById("pickupStatus");
const pickupLatDestiny = document.getElementById("pickupLatDestiny");
const pickupLngDestiny = document.getElementById("pickupLngDestiny");
const pickupLatOrigin = document.getElementById("pickupLatOrigin");
const pickupLngOrigin = document.getElementById("pickupLngOrigin");


export function showActions(e){ 
    pickupActions.classList.remove("d-none");
}

export function hideActions(e){
    const clickedInside = pickupActions.contains(e.target) || impOrigin.contains(e.target);
    if (!clickedInside) {
        pickupActions.classList.add("d-none");
        pickupStatus.classList.add("d-none");
    }
}

export function setStatus(msg) {
    pickupStatus.textContent = msg;
    pickupStatus.classList.remove("d-none");
}

export function getJsonAutoComplete(isOrigin, setPoint) {
    if(isOrigin) {
        return {
                inputEl: impOrigin,
                sugEl: document.getElementById("pickupSug"),
                onSelected: async ({ displayName, lat, lng, raw }) => {
                    let origin = await setPoint(null, lat, lng);
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
            }
    }

    return {
        inputEl: impDestiny,
        sugEl: document.getElementById("dropoffSug"),
        onSelected: async ({ displayName, lat, lng, raw }) => {
            let destiny = await setPoint(null, lat, lng);
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
    }

}