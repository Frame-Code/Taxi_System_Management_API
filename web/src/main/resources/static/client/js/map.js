const lat = -2.170998;   // Guayaquil aprox
const lng = -79.922359;

const map = L.map('map').setView([lat, lng], 13);

//L = Leaflet object

// Tiles de OpenStreetMap
L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  maxZoom: 19,
  attribution: '&copy; OpenStreetMap'
}).addTo(map);

// Un marcador
L.marker([lat, lng]).addTo(map).bindPopup('Punto inicial').openPopup();
