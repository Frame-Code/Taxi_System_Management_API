package dto.http.request;

import Enums.entitiesEnums.STATUS_TAXI;

public record StatusCabDto(
        long id,
        STATUS_TAXI status
) {}
