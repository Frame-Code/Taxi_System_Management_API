package com.taxi.mappers;

import dto.TaxiDTO;
import dto.UserDTO;
import dto.VehicleDTO;
import dto.DriverDTO;
import io.github.frame_code.domain.entities.Taxi;
import io.github.frame_code.domain.entities.User;
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
    @Mapping(target = "driverDTO.userDTO", expression = "java(toUserDTO(driver.getUser()))")
    @Mapping(target = "statusTaxi", source = "status")
    @Mapping(target = "liveAddress", expression = "java(taxi.getTaxiLiveAddresses() != null ? taxi.getTaxiLiveAddresses().getLocation().toString() : null)")
    TaxiDTO toDTO(Taxi taxi);

    VehicleDTO toVehicleDTO(Vehicle vehicle);

    @Mapping(target = "userDTO", source = "user")
    DriverDTO toDriverDTO(Driver driver);

    UserDTO toUserDTO(User user);
}
