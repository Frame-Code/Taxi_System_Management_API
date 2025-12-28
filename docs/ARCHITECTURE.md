# Arquitectura y Módulos

Este repositorio está estructurado como un monorepo Maven con separación clara de responsabilidades.

## Módulos

- web
  - Spring Boot app y controladores (`com.controllers.*`).
  - Manejo global de excepciones (`GlobalExceptionHandler`).
  - Configuración de JPA y escaneo de beans (`TaxiApiApplication`).
- service
  - Lógica de negocio: matching, notificaciones, ride, fare, location.
  - Scheduler para esperar respuesta del taxi.
  - Integraciones HTTP (OpenRouteService) y SDK (OpenCage).
  - Mappers con MapStruct.
- domain
  - Entidades JPA y repositorios Spring Data JPA.
  - Uso de MS SQL Server con tipos espaciales (`geography`) y consultas nativas.
- shared
  - DTOs, enums y utilidades comunes (JTS `Point`, WKT helpers, auditoría).

## Principales Casos de Uso

- Verificación de ubicación soportada
  - `VerifyLocationController` => `ParseCoordinatesService` (OpenCage) => `VerifyLocationService` (reglas/ciudades soportadas).
- Buscar taxis cercanos
  - `MatchCabController.search_cab` => `SearchCabByDistance` => `FindCabsService` => `TaxiLiveAddressRepository.findNearbyTaxis(spGetNearbyCabs)`.
- Obtener info de viaje (distancia/tiempo + precio)
  - `MatchCabController.get_info_ride` => `RideService.getRideInfo(OpenRouteService)` + `getTotalPrice(Fare)`.
- Aceptar viaje
  - `MatchCabController.accept_road` => `RideUseCaseService.acceptRoad` arma entidades y persiste `Road` + cambia `Taxi.status` a WORKING.
- Cambiar estado del viaje
  - `RideStatusController.set` => valida transición con `statusOrder` y persiste.

## Matching y Notificaciones

- Patrón Mediator (`IMatchMediator`/`MatchMediatorImpl`): orquesta envío, verificación y actualización de estado de notificaciones.
- Notificación de viaje (`RoadNotification`) persiste `client`, `taxi`, `title`, `message`, `status` (`REQUEST_STATUS`).
- Envío “push” modelado por `SenderRoadNotificationPushImpl` (persistencia simple) a través de `IRoadNotificationService`.
- Verificador (`RoadNotificationVerifierImpl`) expone un `TaxiResponseDTO` con flags accepted/rejected/timeout/pending.
- Scheduler (`IMatchingScheduler`/`MatchingSchedulerImpl`): usa `ScheduledExecutorService` para:
  - Tarea periódica de consulta del estado de notificación.
  - Tarea única de timeout (13s).

Flujo (resumen):

1) Cliente solicita match; para cada taxi cercano se envía notificación y se espera (polling) la respuesta.
2) Si `ACCEPTED` => se retorna el taxi seleccionado; si `REJECTED` => continúa; si timeout => marca `TIMEOUT`.

Diagrama de estados (PlantUML en raíz `a.puml`/`b.puml`).

## Datos y Persistencia

- Entidades principales:
  - `Taxi`, `Driver`, `Vehicle`, `TaxiLiveAddress` (1–1 con `Address` geográfico).
  - `Road` (viaje) con `startAddress` y `endAddress` (heredan de `Address`), `RideStatus`, `Payment`, `Client`.
  - `RideStatus` controla el flujo mediante `statusRoad` + `statusOrder`.
  - `RoadNotification` gestiona el match con `REQUEST_STATUS`.
- Repositorios clave:
  - `TaxiLiveAddressRepository.findNearbyTaxis(pointWKT, meters)` ejecuta `spGetNearbyCabs`.
  - `IRideStatusRepository.findByStatus(STATUS_ROAD)` y otros estándar JPA.

## Integraciones Externas

- OpenRouteService (`OpenRouteServiceClient`): `GET /v2/directions/driving-car` con `start/end` para distancia y duración.
- OpenCage (`OpenCageClient` via SDK): Reverse geocoding para obtener ciudad/provincia/calle desde lat/lon.

## Configuración y Carga de Entorno

- `web` carga `.env` en `TaxiApiApplication` y asigna `System.setProperty(...)`.
- `domain` tests usan `TestJPAConfig` para crear `DataSource` con esas variables.

## Consideraciones de Diseño

- Separación de capas: web (API) -> service (negocio) -> domain (persistencia) -> shared (contratos y utilidades).
- Uso de MapStruct para mapeos deterministas de entidades a DTOs.
- Tipos espaciales: JTS en código + SQL Server `geography` en DB.

