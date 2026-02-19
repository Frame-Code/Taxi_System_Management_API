// Resolve API base relative to current origin (dev/prod)
export const API_URL = `${window.location.origin}/api`;

export const ENDPOINTS = {
  LOCATION_VALIDATOR: "/location/verify",
  SEARCH_CAB: "/cab/search", 
  INFO_RIDE: "/ride/info",
  PAYMENT: "/payment"
};
