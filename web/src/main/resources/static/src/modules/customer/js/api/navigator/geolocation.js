
export async function getCoordinates(watchId, e, status, handle_error, success) {
    if (!("geolocation" in navigator)) {
        status("Tu navegador no soporta geolocalización.");
        handle_error("Tu navegador no soporta geolocalización.");
        return;
    }

    if(watchId !== null) 
        return;

    status("Solicitando permiso y obteniendo ubicación...");
    
    watchId = navigator.geolocation.watchPosition(async (position) => {
        watchId = await success(position, watchId, e);
    }, (error) => {
        watchId = getCoordinatesError(error, handle_error, status);
    }, { 
        enableHighAccuracy: true, 
        maximumAge: 0, 
        timeout: 15000 
    });
    return watchId
}



function getCoordinatesError(error, handle_error, status) {
    switch(error.code) {
        case error.PERMISSION_DENIED:
            status("Permiso denegado para acceder a la ubicación.");
            handle_error("Permiso denegado para acceder a la ubicación.");
            break;
        case error.POSITION_UNAVAILABLE:
            status("La información de ubicación no está disponible.");
            handle_error("La información de ubicación no está disponible.");
            break;
        case error.TIMEOUT:
            status("La solicitud para obtener la ubicación ha caducado.");
            handle_error("La solicitud para obtener la ubicación ha caducado.");
            break;
        default:
            status("Error desconocido al obtener la ubicación.");
            handle_error("Error desconocido al obtener la ubicación.");
            break;
    }
    watchId = null;
    return watchId;
}