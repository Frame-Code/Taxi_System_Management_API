package dto.entities;

import Enums.entitiesEnums.PERMISSION_NAME;
import Enums.entitiesEnums.ROLE_NAME;

import java.util.List;

public record RoleDto(
        Long id,
        ROLE_NAME name,
        List<PERMISSION_NAME> permissions

) {
}
