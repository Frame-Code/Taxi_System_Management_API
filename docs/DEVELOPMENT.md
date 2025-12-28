# Guía de Desarrollo

## Prerrequisitos

- Java 17, Maven 3.8+
- SQL Server accesible con tipos `geography` habilitados
- Claves de API: OpenRouteService, OpenCage

## Variables de Entorno (.env)

En la raíz del repo, crear `.env`:

```
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=taxi;encrypt=false
DB_USERNAME=sa
DB_PASSWORD=YourStrong!Passw0rd
API_KEY_OPEN_ROUTE_SERVICE=...
API_KEY_OPEN_CAGE=...
```

La app (`web`) y los tests (`domain`) cargarán automáticamente estas variables.

## Build y Run

- Compilar todo el monorepo:
  ```bash
  mvn -DskipTests clean install
  ```
- Ejecutar la app:
  ```bash
  mvn -pl web -am spring-boot:run
  ```
- Probar endpoints (ejemplos):
  ```bash
  curl "http://localhost:8080/api/location_taxi/verify_location?latitude=-2.19&longitude=-79.88"
  curl -X POST "http://localhost:8080/api/cabs/search_cab" \
       -H "Content-Type: application/json" \
       -d '{"latitude":-2.19,"longitude":-79.88}'
  ```

## Base de Datos

- Ejecutar `Migrations/Reset_Schema.sql` para limpiar el esquema (dev).
- Ejecutar `Migrations/Init_maestros.sql` y `Migrations/Init_transactions.sql` para seed inicial.
- Crear SP de proximidad: `Migrations/spGetNearbyCabs.sql`.

## Tests

- Los tests usan la DB real (no H2). Asegúrate de que `.env` apunta a una DB de pruebas.
- Ejecutar:
  ```bash
  mvn -pl domain,service test
  ```

## Notas de Código

- MapStruct genera mapeos en build; no es necesario escribir implementaciones.
- `web/pom.xml` contiene dependencias de seguridad comentadas; si activas seguridad, habilita también configuración correspondiente.

