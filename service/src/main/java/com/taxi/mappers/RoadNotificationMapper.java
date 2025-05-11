package com.taxi.mappers;

import dto.NotificationDTO;
import io.github.frame_code.domain.entities.RoadNotification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoadNotificationMapper {
    RoadNotificationMapper INSTANCE = Mappers.getMapper(RoadNotificationMapper.class);

    @Mapping(target = "clientDTO", source = "client")
    @Mapping(target = "clientDTO.userDTO", source = "client.user")
    @Mapping(target = "clientDTO.coordinatesDTO", source = "client.clientLiveAddress")
    @Mapping(target = "taxiDTO", source = "taxi")
    @Mapping(target = "taxiDTO.vehicleDTO", source = "taxi.vehicle")
    @Mapping(target = "taxiDTO.driverDTO.userDTO", source = "taxi.driver.user")
    @Mapping(target = "taxiDTO.statusTaxi", source = "taxi.status")
    @Mapping(target = "taxiDTO.liveAddress", expression = "java(taxi.getTaxiLiveAddresses() != null ? taxi.getTaxiLiveAddresses().getLocation().toString() : null)")
    NotificationDTO toDTO(RoadNotification roadNotification);
}
