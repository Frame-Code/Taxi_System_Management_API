package dto.http.request;

import Enums.entitiesEnums.STATUS_ROAD;

public record SetStatusDTO(
        STATUS_ROAD status,
        Long idRide
) { }
