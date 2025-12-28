const impOrigin = document.getElementById("imp_origen");
const pickupActions = document.getElementById("pickupActions");
const btnUseCurrentLocation = document.getElementById("btnUseCurrentLocation");
const pickupStatus = document.getElementById("pickupStatus");

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

function getCoordinatesSuccess(pos, watchId, e) {
    const { latitude, longitude, accuracy } = pos.coords;

    impOrigin.value = `${latitude.toFixed(6)}, ${longitude.toFixed(6)} (±${Math.round(accuracy)}m)`;
    setStatus("Ubicación detectada correctamente.");

    navigator.geolocation.clearWatch(watchId);
    watchId = null;
    hideActions(e);
    return watchId;
}

function getCoordinatesError(error, e) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            setStatus("Permiso denegado para acceder a la ubicación.");
            break;
        case error.POSITION_UNAVAILABLE:
            setStatus("La información de ubicación no está disponible.");
            break;
        case error.TIMEOUT:
            setStatus("La solicitud para obtener la ubicación ha caducado.");
            break;
        default:
            setStatus("Error desconocido al obtener la ubicación.");
            break;
    }
    watchId = null;
    return watchId;
}

function getCoordinates(watchId, e) {
    if (!("geolocation" in navigator)) {
        setStatus("Tu navegador no soporta geolocalización.");
        return;
    }

    if(watchId !== null) 
        return;

    setStatus("Solicitando permiso y obteniendo ubicación...");
    watchId = navigator.geolocation.watchPosition((position) => {
        watchId = getCoordinatesSuccess(position, watchId, e);
    }, (error) => {
        watchId = getCoordinatesError(error, e);
    }, { 
        enableHighAccuracy: true, 
        maximumAge: 0, 
        timeout: 15000 
    });
    return watchId
}


function init(){
    let watchId = null;

    //Listeners--------------------------------------------
    impOrigin.addEventListener("click", showActions);
    document.addEventListener("click", hideActions);
    btnUseCurrentLocation.addEventListener("click", (e) => {
        watchId = getCoordinates(watchId, e);
    });

}

//--- Main ---
init();