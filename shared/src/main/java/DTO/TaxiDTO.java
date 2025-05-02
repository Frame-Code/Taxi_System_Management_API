package DTO;

import Enums.entitiesEnums.STATUS_TAXI;

public record TaxiDTO (
        Long id,
        VehicleDTO vehicleDTO,
        DriverDTO driverDTO,
        STATUS_TAXI statusTaxi,
        String liveAddress
) {
}
