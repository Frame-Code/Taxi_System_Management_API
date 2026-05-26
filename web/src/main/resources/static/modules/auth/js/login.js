import { loginUser } from './auth.service.js';
import { showErrorToast } from '../../../shared/components/ui_messages.js';
import { save, Keys } from '../../../app/cache/localstorage.js';

document.querySelector("#btnLogin").addEventListener("click", login);

async function login() {
    const email    = document.querySelector("#email").value.trim();
    const password = document.querySelector("#password").value.trim();

    if (!email || !password) {
        showErrorToast("Por favor completa todos los campos");
        return;
    }

    const data = await loginUser({ email, password });
    if (!data) return; // handleApiError ya mostró el toast de error

    // El token llegó como cookie HttpOnly — el navegador lo enviará solo.
    // Solo guardamos el nombre para mostrarlo en la UI.
    const username = data?.response?.user_name ?? '';
    save(Keys.Username, username, 1440); // 24 h en minutos

    window.location.replace('/modules/customer/views/index.html');
}
