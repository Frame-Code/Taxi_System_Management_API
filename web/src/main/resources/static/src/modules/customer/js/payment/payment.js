import { showErrorToast, showInfoToast } from '../../../../shared/components/ui_messages.js';
import { save, get, Keys } from "../../../../app/cache/localstorage.js"
import { savePaymentMethod } from "../api/internal/payment_service.js"

export const PaymentMethod = Object.freeze({
  DebitCard: 'DEBIT_CARD',
  CreditCard: 'CREDIT_CARD',
  Cash: 'CASH'
});

export function getPaymentMethodName(paymentMethod) {
    switch(paymentMethod) {
        case 'DEBIT_CARD':
            return "Tarjeta de debito";
        case 'CREDIT_CARD':
            return "Tarjeta de credito";
        case 'CASH':
            return "Efectivo";
        default:
            return "Desconocido";
    }
}


const li_price = document.getElementById("li_price");
const div_price = document.getElementById("div_price");
const messageCash = document.getElementById("messageCash");
const messageOther = document.getElementById("messageOther");

export async function SavePayment(paymentMethod, amount) {
    const response = await savePaymentMethod(paymentMethod, amount);
    if(!response) {
        showErrorToast("No se pudo guardar el metodo de pago, intente de nuevo...")
        return;
    }

    //Cache
    let currentRide = get(Keys.CurrentRide);
    if(!currentRide) {
        showErrorToast("Imposible crear un nuevo viaje si no se ha asignado un conductor al viaje, vuelva a intentar")
        setTimeout(() => location.reload(), 3000);
        return;
    }
    currentRide.paymentId = response.id;
    save(Keys.CurrentRide, currentRide, currentRide.minutes);
    return response;
}

export function InitPaymentMethod() {
    document.querySelectorAll('.img-option').forEach(label => {
        label.addEventListener('click', (e) => {
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
            div_price.innerHTML = li_price.innerHTML;
            showInfoToast("Recuerda llevar cambio para facilitar el pago al conductor.");

            //Cache
            let currentRide = get(Keys.CurrentRide);
            if(!currentRide) {
                showErrorToast("Imposible crear un nuevo viaje si no se ha asignado un conductor al viaje, vuelva a intentar")
                setTimeout(() => location.reload(), 3000);
            }
            currentRide.paymentMethod = PaymentMethod.Cash;
            save(Keys.CurrentRide, currentRide, currentRide.minutes);

            break;
        case 'Tarjeta de debito':
        case 'optDebitCard':
            messageCash.classList.add('d-none');
            messageOther.classList.remove('d-none');
            showErrorToast("El método de pago con tarjeta de credito aún no está disponible.");
            /*No implementado el guardado en local storage por este metodo de pago */
            break;
        case 'MasterCard':
        case 'optMasterCard':
            messageCash.classList.add('d-none');
            messageOther.classList.remove('d-none');
            showErrorToast("El método de pago mastercard aún no está disponible.");
            /*No implementado el guardado en local storage por este metodo de pago */
            break;
        default:
            showErrorToast("Método de pago no reconocido.");
    }
}