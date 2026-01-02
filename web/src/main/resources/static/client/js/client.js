import { API_URL, ENDPOINTS } from '../../config/api.config.js';
import { handleApiError } from '../../common/error_handler.js';
import { showSuccessToast } from '../../common/ui_messages.js';

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
        const locationData = await verify_location(latitude, longitude);
        if(locationData) {
            showSuccessToast("Ubicación verificada correctamente.");
            return locationData.response.city + ", " + locationData.response.province + ", " + locationData.response.road;
        }
    } catch (error) {
        await handleApiError(error);
    }
}