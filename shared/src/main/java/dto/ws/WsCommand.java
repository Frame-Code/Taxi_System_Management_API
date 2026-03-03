package dto.ws;

import Enums.entitiesEnums.STATUS_ROAD;

import java.time.LocalDateTime;

public record WsCommand(
        LocalDateTime timeStamp,
        Long idRide,
        STATUS_ROAD status
){ }
