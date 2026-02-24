import { showErrorToast, showInfoToast } from '../../../../shared/components/ui_messages.js';
import { setStatus } from '../api/internal/cab_service.js';
import { save, Keys, get } from "../../../../app/cache/localstorage.js"

export const StatusCab = Object.freeze({
  Enable: 'ENABLE',
  Disable: 'DISABLE',
  Working: 'WORKING'
});


export async function setStatusCab(status) {
    showInfoToast("Cambiando el estado del taxi...");
    const idCab = get(Keys.CurrentRide).id_cab;
    if(!idCab) {
        showErrorToast("Imposible cambiar el estado del taxi si no se ha creado un viaje, vuelva a intentar")
        setTimeout(() => location.reload(), 3000);
        return;
    }

    const response = await setStatus(idCab, status);
    if(!response) {
        showErrorToast("No se pudo cambiar el estado del taxi, intente nuevamente.");
        return;
    }

    showInfoToast("Estado del taxi cambiado exitosamente.");
}