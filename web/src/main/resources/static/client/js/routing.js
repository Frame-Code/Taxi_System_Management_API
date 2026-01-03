import { showErrorToast, showSuccessToast } from "../../common/ui_messages.js";

let routeLayer;

export async function drawRoute(start, end, map) {
  const url = `https://router.project-osrm.org/route/v1/driving/` +
              `${start.lng},${start.lat};${end.lng},${end.lat}` +
              `?overview=full&geometries=geojson`;

  const response = await fetch(url);
  const data = await response.json();

  if (!data.routes || !data.routes.length) {
    showErrorToast("No se pudo calcular la ruta");
  }

  const routeCoords = data.routes[0].geometry.coordinates.map(
    ([lng, lat]) => [lat, lng]
  );

  // Limpia ruta anterior
  routeLayer?.remove();

  // Dibuja nueva ruta
  routeLayer = L.polyline(routeCoords, {
    color: "blue",
    weight: 5
  }).addTo(map);

  // Ajusta el zoom a la ruta
  map.fitBounds(routeLayer.getBounds());
  showSuccessToast("Ruta calculada correctamente");
}