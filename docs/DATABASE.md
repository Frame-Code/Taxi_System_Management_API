# Base de Datos y Geoespacial

## SQL Server + Geography

- El modelo usa `org.locationtech.jts.geom.Point` en JPA y `geography` en SQL Server.
- `Address.location` se mapea a `geography` mediante columna `columnDefinition = "geography"`.
- `TaxiLiveAddress` hereda de `Address` y referencia 1–1 a `Taxi` (posición en vivo).

## Procedimiento spGetNearbyCabs

- Archivo: `Migrations/spGetNearbyCabs.sql`.
- Firma: `spGetNearbyCabs(@pointWTK NVARCHAR(70), @meters INT)`.
- Lógica: retorna direcciones (y `taxi_live_address`) cuya distancia `STDistance` al punto WKT es menor/igual a `@meters` ordenadas por cercanía.
- Invocación desde repositorio:
  ```java
  @Query(value = "EXEC spGetNearbyCabs :point , :meters", nativeQuery = true)
  List<TaxiLiveAddress> findNearbyTaxis(@Param("point") String pointWKT, @Param("meters") double meters );
  ```
  Donde `pointWKT` proviene de `GeolocationUtils.coordinatesToWKT(lat, lon)`.

## Seeds y Reset

- `Migrations/Reset_Schema.sql`: elimina FKs/UQ/índices/vistas y tablas en `dbo` (para dev).
- `Migrations/Init_maestros.sql`: resetea identities y carga catálogos (province, city, role, permission…).
- `Migrations/Init_transactions.sql`: crea índice espacial y datos de prueba (`address`, `taxi_live_address`, `payment`).

## Estados y Flujo

- `RideStatus(statusRoad, statusOrder)`: controla transición de estados de un `Road`.
- `REQUEST_STATUS` en `RoadNotification` para negociación (PENDING, ACCEPTED, REJECTED, TIMEOUT).

