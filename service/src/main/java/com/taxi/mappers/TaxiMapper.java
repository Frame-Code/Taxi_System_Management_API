package com.taxi.mappers;

import DTO.TaxiDTO;
import DTO.VehicleDTO;
import DTO.DriverDTO;
import io.github.frame_code.domain.entities.Taxi;
import io.github.frame_code.domain.entities.Vehicle;
import io.github.frame_code.domain.entities.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaxiMapper {
    TaxiMapper INSTANCE = Mappers.getMapper(TaxiMapper.class);

    @Mapping(target = "vehicleDTO", source = "vehicle")
    @Mapping(target = "driverDTO", source = "driver")
    @Mapping(target = "statusTaxi", source = "status")
    @Mapping(target = "liveAddress", expression = "java(taxi.getTaxiLiveAddresses() != null ? taxi.getTaxiLiveAddresses().getReference() : null)")
    TaxiDTO toDTO(Taxi taxi);

    VehicleDTO toVehicleDTO(Vehicle vehicle);
    DriverDTO toDriverDTO(Driver driver);
}
