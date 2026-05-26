import { showSuccessToast, showErrorToast } from '../../../shared/components/ui_messages.js';
import { save, Keys } from '../../../app/cache/localstorage.js';
import { registerUser } from './auth.service.js';

const d = document;

/**
 * Valida los campos del formulario.
 * @returns {boolean} true si todos los campos son válidos, false si hay errores.
 */
function checkFields() {
    const names      = d.querySelector("#names").value.trim();
    const lastnames  = d.querySelector("#lastnames").value.trim();
    const email      = d.querySelector("#email").value.trim();
    const phone      = d.querySelector("#phone").value.trim();
    const borndate   = d.querySelector("#borndate").value.trim();
    const password   = d.querySelector("#password").value.trim();
    const repeatPass = d.querySelector("#repeatPassword").value.trim();

    if (!names || !lastnames || !email || !phone || !borndate || !password || !repeatPass) {
        showErrorToast("No puede haber ningún campo vacío");
        return false;
    }

    if (password !== repeatPass) {
        showErrorToast("Las contraseñas no coinciden");
        return false;
    }

    if (!/^[0-9]+$/.test(phone)) {
        showErrorToast("Por favor escribe un número de teléfono válido (solo dígitos)");
        return false;
    }

    if (!/^[a-zA-ZÁÉÍÓÚáéíóúÑñ\s]+$/.test(names) || !/^[a-zA-ZÁÉÍÓÚáéíóúÑñ\s]+$/.test(lastnames)) {
        showErrorToast("Los nombres solo pueden contener letras");
        return false;
    }

    return true;
}

async function register() {
    // Bug original: if(checkFields()) return — bloqueaba cuando era VÁLIDO e intentaba continuar cuando era inválido
    if (!checkFields()) return;

    const userData = {
        names:              d.querySelector("#names").value.trim(),       // 'names' coincide con RegisterUserDto.names
        lastnames:          d.querySelector("#lastnames").value.trim(),   // 'lastnames' coincide con RegisterUserDto.lastnames
        email:              d.querySelector("#email").value.trim(),
        phone:              d.querySelector("#phone").value.trim(),
        password:           d.querySelector("#password").value.trim(),
        photo:              null,
        additionalInfoJson: null,
        bornDate:           d.querySelector("#borndate").value.trim(),    // formato ISO yyyy-MM-dd que Jackson deserializa a LocalDate
        rolName:            'CLIENT'                                      // el backend lo ignora y siempre asigna CLIENT
    };

    const data = await registerUser(userData);
    if (!data) return; // handleApiError ya mostró el toast de error

    // El token llegó como cookie HttpOnly — el navegador lo gestiona solo.
    // Solo guardamos el nombre para mostrarlo en la UI.
    const username = data?.response?.user_name ?? '';
    save(Keys.Username, username, 1440); // 24 h en minutos

    showSuccessToast("¡Cuenta creada! Cargando tu sesión...");
    setTimeout(() => window.location.replace('/modules/customer/views/index.html'), 1500);
}

function init() {
    d.querySelector("#btnRegister").addEventListener("click", register);
}

init();
