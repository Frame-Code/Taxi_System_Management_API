import { API_URL, ENDPOINTS } from '../../../../../app/config/api.config.js';
import { handleApiError } from '../../../../../shared/components/error_handler.js';
import { showSuccessToast, showInfoToastStatic, showInfoToast, hideStaticToast } from '../../../../../shared/components/ui_messages.js';


export async function searchCab(latOrigin, lngOrigin) {
    const {toast, clearIntervals} = showInfoToastStatic("Buscando taxis cercanos");
    const url = `${API_URL}${ENDPOINTS.SEARCH_CAB}?latitude=${latOrigin}&longitude=${lngOrigin}`;

    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if(!response.ok) {
        await handleApiError(response);
        hideStaticToast(toast);
        clearIntervals();
        return null;
    }

    let responseData = await response.json();
    if(responseData.status_code == 204) {
        hideStaticToast(toast);
        clearIntervals();
        showInfoToast("No se encontraron taxis cercanos o ninguno esta disponible.");
        showInfoToast("Intenta mas tarde...");
        return null;
    }

    hideStaticToast(toast);
    clearIntervals();
    showSuccessToast("Un taxi ha aceptado tu solicitud!");
    return responseData;
}

export async function setStatus(cabId, status) {
    const url = `${API_URL}${ENDPOINTS.STATUS_CAB}`;
    const payload = {
        id: cabId,
        status: status
    }
    const response = await fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    });

    if(!response.ok) {
        await handleApiError(response);
        return false;
    }

    return true;
}