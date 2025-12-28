% API: Endpoints y Payloads

Todas las respuestas usan `BaseResponse` con campos comunes: `status_code`, `status_message`, `message`, `timeStamp`, `response` (payload específico).

## Health

- GET `/`
  - 200 OK: `"Hello word"`

## Ubicación

- GET `/api/location_taxi/verify_location?latitude={lat}&longitude={lon}`
  - 200 OK (`Location disponible`) o 204 lógico (sin soporte/coordenadas no encontradas) encapsulado en `BaseResponse`.
  - Ejemplo `response`: `{ "latitude": -2.19, "longitude": -79.88 }`

## Cabs

- POST `/api/cabs/search_cab`
  - Body `CoordinatesDTO`:
    ```json
    { "latitude": -2.19, "longitude": -79.88 }
    ```
  - 200 OK `response`: `TaxiDTO[]` o `null` con mensaje "No cabs founded nearby from you".
  - `TaxiDTO`:
    ```json
    {
      "id": 123,
      "vehicleDTO": { "plate": "...", ... },
      "driverDTO": { "id": 1, "userDTO": { "fullNames": "..." }, ... },
      "statusTaxi": "ENABLE|WORKING|DISABLE",
      "liveAddress": "POINT(lon lat)" | null
    }
    ```

- GET `/api/cabs/get_info_ride`
  - Body `FullCoordinatesDTO`:
    ```json
    { "origin": {"latitude": -2.19, "longitude": -79.88},
      "destiny": {"latitude": -2.15, "longitude": -79.89} }
    ```
  - 200 OK `response`: `RideInfoDTO` con:
    ```json
    {
      "distanceInfoDTO": { "approxDistance": 4567.0, "approxSeconds": 782.0 },
      "totalPrice": 3.75
    }
    ```
  - 503/409 si no se puede obtener la ruta.

- POST `/api/cabs/accept_road`
  - Body `AcceptRoadDTO`:
    ```json
    {
      "coordinatesDTO": {
        "origin": {"latitude": -2.19, "longitude": -79.88},
        "destiny": {"latitude": -2.15, "longitude": -79.89}
      },
      "idTaxi": 10,
      "idPayment": 1,
      "idCityOrigin": 2,
      "idCityDestiny": 2
    }
    ```
  - 200 OK: crea `Road` con estado inicial y marca `Taxi` como `WORKING`.
  - Nota: actualmente toma el `Client` con `id=1` desde DB (pendiente parametrizar/autenticar).

## Ride Status

- POST `/api/ride_status/set`
  - Body `SetStatusDTO`:
    ```json
    { "status": "INITIALIZED|ACCEPTED|PICK_UP|ARRIVED|FINISHED", "idRide": 99 }
    ```
  - 200 OK si transición válida; 404 si viaje/estado no existe o secuencia de estado inválida.
  - Lógica: solo permite avanzar si `current.statusOrder < new.statusOrder`.

## Errores

- `GlobalExceptionHandler` mapea exceptions a `BaseResponse` con status 404/500 según caso.
- Excepciones notables: `CityNotFoundException`, `CabNotFoundException`, `RideStatusNotFoundException`, `FareNotFoundException`, `PaymentNotFoundException`.

## Autenticación

- Actualmente no implementada (dependencias de Spring Security comentadas en `web/pom.xml`).

