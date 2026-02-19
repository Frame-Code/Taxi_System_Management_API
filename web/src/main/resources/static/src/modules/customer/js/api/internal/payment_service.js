import { API_URL, ENDPOINTS } from '../../../../../app/config/api.config.js';

export async function SavePaymentMethod(method, amount) {
    const url = `${API_URL}${ENDPOINTS.PAYMENT}`;
    const payload = {
        PAYMENT_METHOD: method,
        amount: amount
    };

    const response = await fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    });

    if (!response.ok) {
        await handleApiError(response);
        return null;
    }

    return await response.json();
}
