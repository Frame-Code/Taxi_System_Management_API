# Roadmap y Pendientes (Sugerido)

## Seguridad y Autenticación

- Habilitar Spring Security + JWT (dependencias comentadas actualmente en `web/pom.xml`).
- Modelar login/registro y roles (`ROLE_NAME`: ADMIN, DRIVER, CLIENT).
- Proteger endpoints de negocio; usar `@PreAuthorize` donde aplique.

## Matching y Notificaciones

- Integración real de push (FCM/OneSignal) para `SenderRoadNotificationPushImpl` en lugar de persistencia simple.
- Endpoint para que el taxista acepte/rechace la notificación (actualmente se asume cambio de estado externo).
- Reintentos/colas para notificaciones fallidas.
- Mejorar scheduler: single-thread por match, cancelar tareas de forma segura; métricas.

## Viajes (Ride)

- Completar ciclo de vida: pickup, arrived, finished; setear `endDate`; liberar taxi (STATUS_TAXI.ENABLE).
- Endpoints de tracking en vivo: actualizar `TaxiLiveAddress` y `TrackingTaxi` durante el viaje.
- Políticas de precio dinámico, promociones, y métodos de pago adicionales.

## API y DX

- Agregar OpenAPI/Swagger (springdoc-openapi) y ejemplos de request/response.
- Validaciones `@Valid` en DTOs (p.ej. lat/lon, ids positivos).
- Estandarizar códigos HTTP (evitar 204 “lógico” dentro de 200) y mensajes.

## Datos y Migraciones

- Scripts de migración con herramienta (Flyway/Liquibase) en lugar de SQL sueltos.
- Datos seed coherentes para entornos (dev/test).
- Índices adicionales según consultas; constraints y FKs revisadas.

## Observabilidad

- Logging estructurado, trazas de negocio (match timelines).
- Métricas (Micrometer/Prometheus) y healthchecks.

## Infraestructura

- Docker Compose (app + SQL Server) con perfiles dev/test.
- CI con Maven + tests por módulo; Quality Gate (SpotBugs/Checkstyle opcional).

