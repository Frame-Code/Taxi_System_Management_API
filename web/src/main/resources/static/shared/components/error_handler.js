import { showErrorToast } from "./ui_messages.js";

function getFriendlyErrorMessage(errorResponse) {
  // Caso 1: backend manda mensaje claro
  if (errorResponse?.message) {
    return errorResponse.message;
  }

  // Caso 2: backend manda errors tipo validation
  if (errorResponse?.errors?.length) {
    return errorResponse.errors[0];
  }

  // Caso 3: fallback
  return "Ocurrió un error inesperado. Intenta nuevamente.";
}

export async function handleApiError(response) {
  let data = null;

  try {
    data = await response.json();
  } catch (_) {}

  const message = getFriendlyErrorMessage(data);
  showErrorToast(message);
}
