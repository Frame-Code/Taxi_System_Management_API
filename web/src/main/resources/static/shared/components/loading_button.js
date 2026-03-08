/**
 * Html minimo necesario para un boton de carga, dentro de un boton:
    <span class="btn-text">Ingresar</span>
    <span class="spinner-border spinner-border-sm d-none" role="status"></span>
 * 
*/
export function setButtonLoading(btn, isLoading) {
  const text = btn.querySelector(".btn-text");
  const spinner = btn.querySelector(".spinner-border");

  btn.disabled = isLoading;

  if (text) text.classList.toggle("d-none", isLoading);
  if (spinner) spinner.classList.toggle("d-none", !isLoading);
}
