import { showErrorToast } from "../../../common/ui_messages.js";

export async function searchAddressNominatim(query, { signal, countryCodes = "ec", limit = 6 } = {}) {
  const url = new URL("https://nominatim.openstreetmap.org/search");
  url.searchParams.set("format", "json");
  url.searchParams.set("q", query);
  url.searchParams.set("addressdetails", "1");
  url.searchParams.set("limit", String(limit));
  url.searchParams.set("countrycodes", countryCodes);


  const res = await fetch(url.toString(), {
    method: "GET",
    signal,
    headers: {
      "Accept": "application/json",
      "Accept-Language": "es"
    }
  });

  if (!res.ok) {
    showErrorToast("Error al buscar la dirección, consulte a sistemas");
    return null;
  }

  return res.json();
}
