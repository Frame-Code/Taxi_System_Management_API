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
    @Mapping(target = "taxiDTO", source = "taxi")
    NotificationDTO toDTO(RoadNotification roadNotification);
}
