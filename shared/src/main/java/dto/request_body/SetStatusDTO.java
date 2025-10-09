package dto.request_body;

import Enums.entitiesEnums.STATUS_ROAD;

public record SetStatusDTO(
        STATUS_ROAD status,
        Long idRide
) { }
