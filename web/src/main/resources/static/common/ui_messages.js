const ToastType = Object.freeze({
  Error: 'danger',
  Success: 'success',
  Info: 'info'
});


export function showErrorToast(message) {
    showToast(message, ToastType.Error);
}

export function showSuccessToast(message) {
    showToast(message, ToastType.Success);
}

export function showInfoToast(message) {
    showToast(message, ToastType.Info);
}

function showToast(message, type) {
    const container = document.getElementById("toastContainer");

    const toastEl = document.createElement("div");
    toastEl.className = "toast align-items-center text-bg-" + type + " border-0";
    toastEl.setAttribute("role", "alert");

    toastEl.innerHTML = `
        <div class="d-flex">
        <div class="toast-body">
            ${message}
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    `;

    container.appendChild(toastEl);

    const toast = new bootstrap.Toast(toastEl, { delay: 5000 });
    toast.show();

    toastEl.addEventListener("hidden.bs.toast", () => toastEl.remove());
}