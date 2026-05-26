# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Comandos Esenciales

### Build y Run
```bash
# Compilar todo el monorepo (sin tests)
mvn -DskipTests clean install

# Arrancar la aplicación (puerto 8080)
mvn -pl web -am spring-boot:run

# Compilar un módulo específico
mvn -pl service -am install
```

### Tests
```bash
# Ejecutar tests de módulos con tests reales (requiere DB activa)
mvn -pl domain,service test

# Test único por clase
mvn -pl domain test -Dtest=TaxiLiveAddressRepositoryTest

# Test único por método
mvn -pl domain test -Dtest=TaxiLiveAddressRepositoryTest#nombreDelMetodo
```

> **Los tests requieren la DB real en SQL Server.** No hay H2 ni mocks de persistencia. Asegúrate de que `.env` apunta a una base de datos de pruebas antes de ejecutar.

### Base de Datos (dev/reset)
Los scripts en `Migrations/` se ejecutan manualmente en SQL Server en este orden:
1. `Reset_Schema.sql` — limpia el esquema
2. `Init_maestros.sql` — catálogos (provincias, ciudades, roles, permisos)
3. `Init_transactions.sql` — datos de prueba + índice espacial
4. `spGetNearbyCabs.sql` — procedimiento almacenado de proximidad

## Variables de Entorno

La app carga `.env` desde la raíz del repositorio al arrancar (`Main.java`). Crear el archivo con:

```
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=taxi;encrypt=false
DB_USERNAME=sa
DB_PASSWORD=YourStrong!Passw0rd
API_KEY_OPEN_ROUTE_SERVICE=...
API_KEY_OPEN_CAGE=...
SECURITY_JWT_SECRET_KEY=...
```

## Arquitectura del Monorepo

Cuatro módulos Maven con dependencias unidireccionales: `web` → `service` → `domain` → `shared`.

- **`shared`** — DTOs, enums y utilidades. Sin Spring. `GeolocationUtils` convierte coordenadas a WKT para consultas espaciales. DTOs de request en `dto/http/request/`, de response en `dto/http/response/`, contratos internos en `dto/in/` y `dto/out/`.
- **`domain`** — Entidades JPA y repositorios Spring Data. Usa SQL Server con tipos `geography` (mapeados a `org.locationtech.jts.geom.Point` en JPA). La consulta espacial clave es `TaxiLiveAddressRepository.findNearbyTaxis(pointWKT, meters)` que ejecuta `EXEC spGetNearbyCabs`.
- **`service`** — Lógica de negocio organizada en módulos por paquete: `find_cabs_module`, `matcher_module`, `notification_module`, `location_module`, `fare_module`. Contiene mappers MapStruct y clientes HTTP externos.
- **`web`** — Spring Boot app (`com.main.Main`). Define `@ComponentScan`, `@EntityScan` y `@EnableJpaRepositories` explícitamente; **cualquier nuevo paquete de beans debe registrarse aquí**.

## Flujo de Matching (núcleo del sistema)

El matching taxi-cliente funciona con polling bloqueante en el hilo de la petición HTTP:

1. `CabController` → `SearchCabByDistance` (patrón Abstract Factory/Template) → `TaxiLiveAddressRepository.findNearbyTaxis` (SP espacial)
2. Para cada taxi candidato: `MatchMediatorImpl.match()` → `MatchService.initMath()`
3. `MatchService` persiste una `RoadNotification` (estado `PENDING`) y lanza dos tareas con `ScheduledExecutorService`:
   - **Tarea periódica** (cada 2s): consulta el estado de la notificación; si `ACCEPTED` retorna el taxi, si `REJECTED` continúa al siguiente.
   - **Tarea de timeout** (13s): si sigue `PENDING`, marca `TIMEOUT`.
4. `executorPeriod.awaitTermination(13s)` bloquea hasta resolución o timeout.

El taxi "acepta/rechaza" cambiando el `REQUEST_STATUS` de `RoadNotification` externamente (aún no hay endpoint dedicado para esto).

## Datos y Persistencia

- `Address` es la entidad base geoespacial con campo `location` (`geography` en SQL Server, `Point` JTS en JPA). `TaxiLiveAddress` hereda de `Address` y registra la posición en tiempo real del taxi (relación 1-1 con `Taxi`). `RoadAddress` también hereda de `Address` y se usa para origen/destino del viaje.
- `Road` es el viaje: tiene `startAddress`, `endAddress`, `RideStatus`, `Payment`, `Client`, `Taxi`.
- `RideStatus` controla el ciclo de vida mediante `statusRoad` (enum `STATUS_ROAD`) y `statusOrder` (entero). Las transiciones sólo avanzan: `current.statusOrder < new.statusOrder`.
- `RoadNotification` gestiona la negociación: campos `client`, `taxi`, `title`, `message`, `status` (`REQUEST_STATUS`: PENDING / ACCEPTED / REJECTED / TIMEOUT).

## Integraciones Externas

- **OpenRouteService** (`OpenRouteServiceClient`, WebFlux `WebClient`): `GET /v2/directions/driving-car?start={lon,lat}&end={lon,lat}` — devuelve distancia y duración para calcular precio con `FareService`.
- **OpenCage** (`OpenCageClient`, SDK `jopencage`): reverse geocoding de lat/lon a ciudad/provincia/calle — usado por `ParseCoordinatesServiceImpl` antes de `VerifyLocationServiceImpl`.

## MapStruct

Los mappers están en `service/src/main/java/com/taxi/mappers/`. MapStruct genera implementaciones en tiempo de compilación; **no escribir implementaciones manuales**. Al agregar un nuevo mapper, incluirlo en los `annotationProcessorPaths` del `service/pom.xml` si es necesario (ya configurado para Lombok + MapStruct).

## Seguridad / JWT

Spring Security y JWT (`com.auth0:java-jwt`) están presentes en `web/pom.xml` y hay código base en `com.security` (`JwtUtils`, `JwtTokenValidator`, `UserDetailsServiceImpl`). La seguridad está activa pero los endpoints de negocio no están protegidos aún (`SecurityConfig` los permite todos). Para proteger nuevos endpoints usar `@PreAuthorize` y configurar `SecurityConfig`.

## WebSocket

`WebSocketConfig` y `RideWsController` están presentes pero sin implementación funcional completa. Los DTOs `WsCommand`/`WsResponse` están en `shared`.

## Estado Actual

- Auth/JWT: código presente, endpoints sin proteger.
- `POST /api/ride/accept` toma el `Client` con `id=1` hardcodeado (pendiente autenticación).
- Notificaciones push: simuladas como persistencia/consulta de `RoadNotification`, no hay FCM/OneSignal.
- Tracking en tiempo real: entidad `TrackingTaxi` existe pero sin endpoints ni lógica.
- No hay Swagger/OpenAPI; la referencia de API está en `docs/API.md`.
