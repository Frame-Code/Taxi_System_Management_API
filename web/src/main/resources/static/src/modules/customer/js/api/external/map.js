import { drawRoute } from './routing.js';
import { showErrorToast } from "../../../../../shared/components/ui_messages.js";

let map;
let pickupMarker;

let pickUpMakerOrigin;
let pickUpMakerDestiny;

//L = Leaflet object
export function initializeMap(destiny, origin) {
  map = L.map('map').setView([-2.170998, -79.922359], 13);

  // Tiles de OpenStreetMap
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; OpenStreetMap'
  }).addTo(map);

  map.on('click', (e) => {
    const { lat, lng } = e.latlng;
    if(typeof destiny === 'function' || typeof origin === 'function') {
      setPickupMarkerCustom(lat, lng, destiny, origin);
    }
  });
}

export function setPickupMarkerOrigin(lat, lng, msg) {
  pickUpMakerOrigin?.remove();
  pickUpMakerOrigin = null;
  pickUpMakerOrigin = L.marker([lat, lng])
  .addTo(map)
  .bindPopup(msg)
  .openPopup();
  if(pickUpMakerDestiny) {
    drawRouteWrapper();
  }
}

export function setPickupMarkerDestiny(lat, lng, msg) {
  pickUpMakerDestiny?.remove();
  pickUpMakerDestiny = null;
  pickUpMakerDestiny = L.marker([lat, lng])
  .addTo(map)
  .bindPopup(msg)
  .openPopup();
  if(pickUpMakerOrigin) {
    drawRouteWrapper();
  }
}

function setPickupMarkerCustom(latitude, longitude, destiny, origin) {
  pickupMarker = L.marker([latitude, longitude])
  .addTo(map)
  .bindPopup(buildPickupPopup());

  pickupMarker.on('popupopen', (e) => {
    if(typeof destiny === 'function') {
      document.getElementById('btn_select_destination').addEventListener('click', async (ev) => {
        let destinyResponse = await destiny(ev, latitude, longitude);
        if(!destinyResponse) {
            clearPickupMarker();
            return;
        }

        pickupMarker.closePopup();
        const { lat, lng } = pickupMarker.getLatLng();
        setPickupMarkerDestiny(lat, lng, 'Tu destino');
        clearPickupMarker();

        if(pickUpMakerOrigin) {
          drawRouteWrapper();
        }
      });
    }

    if(typeof origin === 'function') {
      document.getElementById('btn_select_origin').addEventListener('click', async (ev) => {
        let originResponse = await origin(ev, latitude, longitude);
        if(!originResponse) {
            clearPickupMarker();
            return;
        }

        pickupMarker.closePopup();
        const { lat, lng } = pickupMarker.getLatLng();
        setPickupMarkerOrigin(lat, lng, 'Tu lugar de recogida');
        clearPickupMarker();
        
        if(pickUpMakerDestiny) {
          drawRouteWrapper();
        }
      });
    }

    document.getElementById('btn_clean').addEventListener('click', (ev) => {
      pickupMarker.remove();
      pickupMarker = null;
    }); 
    
  });

  pickupMarker.openPopup();
}

export function clearPickupMarker() {
  pickupMarker?.remove();
  pickupMarker = null;
}

export function clearPickupMarkerOrigin() {
  pickUpMakerOrigin?.remove();
  pickUpMakerOrigin = null;
}

export function clearPickupMarkerDestiny() {
  pickUpMakerDestiny?.remove();
  pickUpMakerDestiny = null;
}

function drawRouteWrapper() {
  drawRoute(
    { lat: pickUpMakerOrigin.getLatLng().lat, lng: pickUpMakerOrigin.getLatLng().lng },
    { lat: pickUpMakerDestiny.getLatLng().lat, lng: pickUpMakerDestiny.getLatLng().lng },
    map
  ).catch((error) => {
    showErrorToast("Error al dibujar la ruta");
    console.error("Error al dibujar la ruta:", error);
  });
}

function buildPickupPopup() {
  return `
    <button class="btn btn-outline-primary rounded-pill px-2 py-1 shadow" style="margin-top: 8px;" id="btn_select_destination">
        <span class="d-flex align-items-center">
            <i class="bi bi-geo-alt" style="padding-right: 5px;"></i>
            <span class="btn-text fs-9">Mi destino</span>
            <span class="spinner-border spinner-border-sm d-none" role="status"></span>
        </span>
    </button>
    <button class="btn btn-outline-primary rounded-pill px-2 py-1 shadow" style="margin-top: 8px;" id="btn_select_origin">
        <span class="d-flex align-items-center">
            <i class="bi bi-geo-alt" style="padding-right: 5px;" ></i>
            <span class="btn-text fs-9">Recogida</span>
            <span class="spinner-border spinner-border-sm d-none" role="status"></span>
        </span>
    </button>
    <button class="btn btn-outline-primary rounded-pill px-2 py-1 shadow" style="margin-top: 8px;" id="btn_clean">
        <span class="d-flex align-items-center">
            <i class="bi bi-eraser" style="padding-right: 5px;" ></i>
            <span class="fs-9">Quitar</span>
        </span>
    </button>
  `;
}

