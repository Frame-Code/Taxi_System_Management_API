/**
 * Guard de autenticación para páginas protegidas.
 *
 * Llama a GET /api/auth/me con la cookie HttpOnly que el navegador envía
 * automáticamente. Si el backend devuelve 401 (sesión inválida o expirada)
 * redirige al login y lanza una excepción para detener la inicialización
 * de la página. Si la sesión es válida devuelve el objeto con el username.
 *
 * Uso:
 *   import { requireAuth } from '/app/guards/auth_guard.js';
 *   const session = await requireAuth();   // redirige si no hay sesión
 */

const LOGIN_URL = '/modules/auth/login.html';
const ME_URL    = `${window.location.origin}/api/auth/me`;

export async function requireAuth() {
    let response;
    try {
        response = await fetch(ME_URL, {
            method: 'GET',
            credentials: 'include'   // incluye la cookie access_token
        });
    } catch (_) {
        // Sin red — no podemos validar; redirige a login por seguridad
        window.location.replace(LOGIN_URL);
        throw new Error('AUTH_REDIRECT');
    }

    if (response.status === 401) {
        window.location.replace(LOGIN_URL);
        throw new Error('AUTH_REDIRECT');
    }

    if (!response.ok) {
        // Cualquier otro error del servidor: deja pasar pero avisa en consola
        console.warn('[auth_guard] Respuesta inesperada de /api/auth/me:', response.status);
        return null;
    }

    const data = await response.json();
    return data?.response ?? null;   // { username: "email@example.com" }
}
