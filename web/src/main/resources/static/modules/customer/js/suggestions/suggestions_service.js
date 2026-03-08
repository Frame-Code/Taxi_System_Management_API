import { showErrorToast } from "../../../../shared/components/ui_messages.js";
import { searchAddressNominatim } from "../api/external/addresses.js";

function debounce(fn, delay = 350) {
  let t = null;
  return (...args) => {
    clearTimeout(t);
    t = setTimeout(() => fn(...args), delay);
  };
}

function showSuggestions(container, items, onPick) {
  container.innerHTML = "";
  if (!items.length) {
    container.innerHTML = `<div class="border rounded bg-white mt-1 p-2 d-none">Sin resultados</div>`;
    container.style.display = "block";
    return;
  }


  for (const it of items) {
    const div = document.createElement("div");
    div.className = "border rounded bg-white mt-1 p-2 suggestion-item";
    div.textContent = it.display_name;
    div.addEventListener("click", () => onPick(it));
    container.appendChild(div);
  }
  container.style.display = "block";
}

function hideSuggestions(container) {
  container.style.display = "none";
  container.innerHTML = "";
}

export async function attachAutocomplete(
    {
    inputEl,
    sugEl,
    onSelected
    }
) {
  let controller = null;
  let lastQuery = "";

    const run = debounce(async () => {
        const q = inputEl.value.trim();
        if (q.length < 3) {
            hideSuggestions(sugEl);
            return;
        }

        // evitar repetir la misma búsqueda
        if (q === lastQuery) 
            return;

        lastQuery = q;

        // cancelar request anterior
        if (controller) 
            controller.abort();

        controller = new AbortController();
        try {
            const results = await searchAddressNominatim(q, { signal: controller.signal });
            showSuggestions(sugEl, results, (picked) => {
                inputEl.value = picked.display_name;
                hideSuggestions(sugEl);
                onSelected({
                    displayName: picked.display_name,
                    lat: Number(picked.lat),
                    lng: Number(picked.lon),
                    raw: picked
                });
            });
        } catch (err) {
            if (err.name === "AbortError") 
                return;
            showErrorToast("Error al buscar direcciones");
            sugEl.innerHTML = `<div class="item muted">Error consultando direcciones</div>`;
            sugEl.style.display = "block";
        }
    }, 350);

    inputEl.addEventListener("input", run);
    inputEl.addEventListener("blur", () => setTimeout(() => hideSuggestions(sugEl), 150));

    // si vuelve a enfocar y hay texto suficiente, puedes mostrar de nuevo
    inputEl.addEventListener("focus", () => {
        if (inputEl.value.trim().length >= 3 && sugEl.innerHTML) 
            sugEl.style.display = "block";
  });
}
