import { showErrorToast, showInfoToast } from '../../../../shared/components/ui_messages.js';

const li_price = document.getElementById("li_price");
const div_price = document.getElementById("div_price");
const messageCash = document.getElementById("messageCash");
const messageOther = document.getElementById("messageOther");

export function InitPaymentMethod() {
    document.querySelectorAll('.img-option').forEach(label => {
        label.addEventListener('click', (e) => {
            console.log(e.target.htmlFor)

            const selected = e.target.alt == undefined? e.target.htmlFor : e.target.alt;
            process(selected);
        });
    });
}

function process(selected) {
    switch(selected) {
        case 'Efectivo':
        case 'optCash':
            messageCash.classList.remove('d-none');
            messageOther.classList.add('d-none');
            console.log(li_price.innerHTML);
            div_price.innerHTML = li_price.innerHTML;
            showInfoToast("Recuerda llevar cambio para facilitar el pago al conductor.");
            break;
        case 'Visa':
        case 'optVisa':
            messageCash.classList.add('d-none');
            messageOther.classList.remove('d-none');
            showErrorToast("El método de pago con Visa aún no está disponible.");
            break;
        case 'MasterCard':
        case 'optMasterCard':
            messageCash.classList.add('d-none');
            messageOther.classList.remove('d-none');
            showErrorToast("El método de pago mastercard aún no está disponible.");
            break;
        case 'PayPal':
        case 'optPayPal':
            messageCash.classList.add('d-none');
            messageOther.classList.remove('d-none');
            showErrorToast("El método de pago con PayPal aún no está disponible.");
            break;
        default:
            showErrorToast("Método de pago no reconocido.");
    }
}