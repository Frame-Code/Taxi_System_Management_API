import { API_URL, ENDPOINTS } from '../../../../../app/config/api.config.js';
import { handleApiError } from '../../../../../shared/components/error_handler.js';
import { showSuccessToast, showInfoToast } from '../../../../../shared/components/ui_messages.js';


export async function verify_location(latitude, longitude) {
    const url = `${API_URL}${ENDPOINTS.LOCATION_VALIDATOR}?latitude=${latitude}&longitude=${longitude}`;

    const response = await fetch(url, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    });

    if (!response.ok) {
        await handleApiError(response);
        return null;
    }

    return await response.json();
}

export async function get_location_name(latitude, longitude) {
    try {
        showInfoToast("Verificando ubicación...");
        const locationData = await verify_location(latitude, longitude);
        if(!locationData) {
            return null;
        }

        showSuccessToast("Ubicación verificada correctamente.");
        return locationData.response.city + ", " + locationData.response.province + ", " + locationData.response.road;
    } catch (error) {
        await handleApiError(error);
        return null;
    }
}