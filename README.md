# Taxi API (Uber-like) — Monorepo

Proyecto multi-módulo (Maven) para una API tipo Uber: búsqueda de taxis cercanos, negociación/aceptación de viajes, cálculo de ruta y precio, y gestión del ciclo de vida del viaje.

- Módulos: `web` (REST API), `service` (dominio de negocio), `domain` (JPA + Repositorios), `shared` (DTOs/Enums/Utils).
- Base de datos: SQL Server con tipos espaciales (`geography`) y un procedimiento almacenado para cabs cercanos.
- Integraciones: OpenRouteService (direcciones/tiempos/distancia), OpenCage (reverse geocoding).

## Estructura

- `web/`: Spring Boot app + controladores REST.
- `service/`: servicios de negocio (matching, notificaciones, ride, location…).
- `domain/`: entidades JPA, repositorios y consultas nativas (spatial/MS SQL).
- `shared/`: DTOs, enums y utilidades (JTS/Geolocalización).
- `Migrations/`: scripts SQL para reset/seed y `spGetNearbyCabs`.
- `docs/`: diagramas y documentación técnica.

## Requisitos

- Java 17
- Maven 3.8+
- SQL Server con soporte `geography`
- Variables de entorno (se leen via `.env`):
  - `DB_URL` (e.g. `jdbc:sqlserver://localhost:1433;databaseName=taxi;encrypt=false`)
  - `DB_USERNAME`, `DB_PASSWORD`
  - `API_KEY_OPEN_ROUTE_SERVICE`
  - `API_KEY_OPEN_CAGE`

La app carga `.env` al inicio (java-dotenv) y setea los system properties.

## Puesta en marcha

1) Crear base de datos + schemas. Opcional: ejecutar scripts en `Migrations/` para reset/seed y SP.
2) Configurar `.env` en el directorio raíz con las claves/DB.
3) Construir y arrancar:

```
mvn -q -DskipTests clean install
mvn -q -pl web -am spring-boot:run
```

App expuesta en `http://localhost:8080`.

## Endpoints clave (resumen)

- `GET /` — health simple.
- `GET /api/location/verify?latitude={lat}&longitude={lon}`
- `POST /api/cab/search` — body: `CoordinatesDTO`
- `GET /api/ride/info` — body: `FullCoordinatesDTO`
- `POST /api/rideaccept` — body: `AcceptRoadDTO`
- `POST /api/ride/status` — body: `SetStatusDTO`

Detalles completos y ejemplos en `docs/API.md`.

## Arquitectura (vista rápida)

- Matching Mediator + Scheduler para esperar respuesta del taxi (aceptado/rechazado/timeout).
- Notificaciones persistidas (`RoadNotification`) con estado `REQUEST_STATUS`.
- Búsqueda de taxis por proximidad usando SQL Server `STDistance` vía `spGetNearbyCabs`.
- Cálculo de ruta (distancia/tiempo) con OpenRouteService; precio por tarifas (`Fare`).

Más en `docs/ARCHITECTURE.md` y `docs/DATABASE.md`.

## Estado actual y próximos pasos

- Seguridad deshabilitada (dependencias comentadas). No hay auth/JWT.
- Notificaciones “push” simuladas como persistencia/consulta de estado.
- Faltan endpoints para tracking en vivo y sesiones de usuario.
- Ver `docs/ROADMAP.md` para tareas sugeridas y backlog.

## Tests

- Pruebas JPA para repositorios y de integración para `RideServiceImpl`.
- Requieren DB accesible (no usa H2). Config en `domain/config/TestJPAConfig`.

---

Ver documentación extendida en `docs/`.

