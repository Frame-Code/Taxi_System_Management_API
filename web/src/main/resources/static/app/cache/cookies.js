import { showErrorToast } from "./../../shared/components/ui_messages.js";

function encode(v) { 
    return encodeURIComponent(String(v)); 
}

function decode(v) { 
    try { 
        return decodeURIComponent(v);
    } catch { 
        return v; 
    } 
}

export function saveCookie(name, value, options = {}) {
    if (!name || /[=,; \t\r\n\f\v]/.test(name)) {
        showErrorToast("Error al guardar la información en cookies, intente nuevamnete");
        throw new Error(`Nombre de cookie inválido: "${name}"`);
    }

    const {
      days     = null,
      expires  = null,
      maxAge   = null,
      path     = "/",
      domain   = "",
      secure   = location.protocol === "https:",
      sameSite = "Strict",
    } = options;

    let cookieStr = `${encode(name)}=${encode(value)}`;

    if (maxAge !== null) {
      cookieStr += `; max-age=${maxAge}`;
    } else if (days !== null) {
      cookieStr += `; max-age=${Math.round(days * 86400)}`;
    } else if (expires instanceof Date) {
      cookieStr += `; expires=${expires.toUTCString()}`;
    }

    if (path)     
        cookieStr += `; path=${path}`;
    if (domain)   
        cookieStr += `; domain=${domain}`;
    if (secure)   
        cookieStr += `; secure`;
    if (sameSite) 
        cookieStr += `; samesite=${sameSite}`;

    document.cookie = cookieStr;
  }