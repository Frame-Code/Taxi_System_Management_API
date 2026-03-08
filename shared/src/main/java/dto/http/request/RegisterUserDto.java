package dto.http.request;

import Enums.entitiesEnums.ROLE_NAME;

import java.time.LocalDate;

public record RegisterUserDto(
        String names,
        String lastnames,
        String email,
        String phone,
        String password,
        String photo,
        String additionalInfoJson,
        LocalDate bornDate,
        ROLE_NAME rolName
) {
}
