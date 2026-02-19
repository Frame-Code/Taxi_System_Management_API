import { API_URL, ENDPOINTS } from '../../../../../app/config/api.config.js';
import { handleApiError } from '../../../../../shared/components/error_handler.js';

export async function getInfoRide(latOrigin, lngOrigin, latDestiny, lngDestiny) {
    const url = `${API_URL}${ENDPOINTS.INFO_RIDE}`;
    const payload = {
        origin: {
            latitude: latOrigin,
            longitude: lngOrigin
        },
        destiny: {
            latitude: latDestiny,
            longitude: lngDestiny
        }
    }

    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    });

    if(!response.ok) {
        await handleApiError(response);
        return null;
    }

    return await response.json();
}

