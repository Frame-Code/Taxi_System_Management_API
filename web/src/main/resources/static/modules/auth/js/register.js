import { RoleName } from './../../../shared/components/enums/role_name';
import { showSuccessToast, showErrorToast } from "./../../../shared/components/ui_messages.js";
import { save, Keys } from "./../../../app/cache/localstorage.js"
import { registerUser } from './auth.service.js';
import { getTokenInfo } from '../utils/jwt.utils.js';
import { saveCookie } from '../../../app/cache/cookies.js';

const d = document;

function checkFields() {
    if (d.querySelector("#password").value.trim() !== d.querySelector("#repeatPassword").value.trim()) {
        showErrorToast("Las contraseñas no coinciden");
        return false;
    }

    if(d.querySelector("#names").value == "" || d.querySelector("#lastnames").value == ""||
        d.querySelector("#email").value == "" || d.querySelector("#phone").value == "" || 
        d.querySelector("#borndate").value == "" || d.querySelector("#password").value == "" ||
        d.querySelector("#repeatPassword").value == "") {
        showErrorToast("No puede haber ningun campo vacio");
        return false;
    }

    if(!/^[0-9]+$/.test(d.querySelector("#phone").value)) {
        showErrorToast("Por favor escribe un numero de telefono valido");
        return false;
    }

    if(!/^[a-zA-Z]+$/.test(d.querySelector("#names").value) ||
        !/^[a-zA-Z]+$/.test(d.querySelector("#lastnames").value)) {
        showErrorToast("Por favor escribe nombres validos");
        return false;
    }

    return true;
}


async function register() {
    if(checkFields()) {
        return;
    }

    const userData = {
        name: d.querySelector("#names").value.trim(),
        lastName: d.querySelector("#lastnames").value.trim(),
        email: d.querySelector("#email").value.trim(),
        phone: d.querySelector("#phone").value.trim(),
        password: d.querySelector("#password").value.trim(),
        photo: null,
        additionalInfoJson: null,
        bornDate: d.querySelector("#borndate").value.trim(),
        rolName: RoleName.Customer
    };
    
    const response = await registerUser(userData);

    if(!response) {
        return;
    }

    if(!request.ok) {
        if(request.status == 409) {
            alert(`Error: ${await request.text()}`)
        }
        throw new Error(`Http error! status ${request.status}`);
    }

    saveCookie("access_token", response.access_token, {path: "/", sameSite: "Lax"})
    const tokenInfo = getTokenInfo(response.access_token);
    save(Keys.Username, response.user_name, tokenInfo.remaining);

    showSuccessToast("Nuevo usuario creado correctamente, cargando...");
    const url = window.location.href;
    setTimeout(() => window.location.replace(`${url.hostname}/modules/customer/views/index.html`), 2000);
}

function init() {
    d.querySelector("#btnRegister").addEventListener("click", register);
}

init();