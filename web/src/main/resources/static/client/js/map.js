let map;
let pickupMarker;

//L = Leaflet object
export function initializeMap() {
  map = L.map('map').setView([-2.170998, -79.922359], 13);

  // Tiles de OpenStreetMap
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    maxZoom: 19,
    attribution: '&copy; OpenStreetMap'
  }).addTo(map);
}

export function setPickupMarker(lat, lng, msg) {
  //if (!pickupMarker) {
  pickupMarker = L.marker([lat, lng]).addTo(map).bindPopup(msg).openPopup();
  /*} else {
    pickupMarker.setLatLng([lat, lng]);
  }*/
}

export function clearPickupMarker() {
  pickupMarker?.remove();
  pickupMarker = null;
}