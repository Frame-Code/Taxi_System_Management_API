import { ENDPOINTS } from '../../../app/config/api.config.js';
import { handleApiError } from '../../../shared/components/error_handler.js';

const AUTH_BASE = window.location.origin;

export async function loginUser(payload) {
    const url = `${AUTH_BASE}${ENDPOINTS.LOGIN}`; 

    const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (!response.ok) {
        await handleApiError(response);
        return null;
    }

    return await response.json();
}

export async function registerUser(payload) {
    const url = `${AUTH_BASE}${ENDPOINTS.REGISTER}`; 

    const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    });

    if (!response.ok) {
        await handleApiError(response);
        return null;
    }

    return await response.json();
}

export async function logoutUser() {
    const url = `${AUTH_BASE}/auth/log-out`;

    await fetch(url, { method: 'POST' });
    window.location.replace('/modules/auth/login.html');
}
