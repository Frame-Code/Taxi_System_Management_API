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

export function showInfoToastStatic(message) {
    return showStaticToast(message, ToastType.Info);
}

function showToast(message, type) {
    const container = document.getElementById("toastContainer");

    const toastEl = document.createElement("div");
    toastEl.className = "toast align-items-center text-bg-" + type + " border-0";
    toastEl.setAttribute("role", "alert");

    toastEl.innerHTML = buildInnerHTML(message);
    container.appendChild(toastEl);

    const toast = new bootstrap.Toast(toastEl, { delay: 5000 });
    toast.show();

    toastEl.addEventListener("hidden.bs.toast", () => toastEl.remove());
}

function showStaticToast(message, type) {
    const container = document.getElementById("toastContainer");

    const toastEl = document.createElement("div");
    toastEl.className = "toast align-items-center text-bg-" + type + " border-0";
    toastEl.setAttribute("role", "alert");

    toastEl.innerHTML = buildInnerHTML(message);
    container.appendChild(toastEl);

    const toast = new bootstrap.Toast(toastEl, { autohide: false });
    toast.show();   

    const interval0 = setInterval(() => {
        toastEl.innerHTML = buildInnerHTML(message + ".");
    }, 1500);

    const interval1 = setInterval(() => {
        toastEl.innerHTML = buildInnerHTML(message + "..");
    }, 2500);

    const interval2 = setInterval(() => {
        toastEl.innerHTML = buildInnerHTML(message + "...");
    }, 3500);


    return {
        toast: toast,
        clearIntervals: function() {            
            clearInterval(interval0);
            clearInterval(interval1);
            clearInterval(interval2);
        }
    }
}

function buildInnerHTML(message) {
    return `
        <div class="d-flex">
        <div class="toast-body">
            ${message}
        </div>
        <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
        </div>
    `;
}

export function hideStaticToast(toast) {
    toast.hide();
}


