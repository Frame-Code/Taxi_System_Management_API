import { API_URL, ENDPOINTS } from '../../../app/config/api.config.js';
import { handleApiError } from '../../../shared/components/error_handler.js';

export async function registerUser(payload) {
    const url = `${API_URL}${ENDPOINTS.REGISTER}`;

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
