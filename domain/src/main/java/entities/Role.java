package entities;

import Enums.entitiesEnums.ROLE_NAME;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter@Setter
public class Role {
    private Long id;
    private ROLE_NAME roleName;
    private boolean isDeleted;
    private LocalDate createdAt;
}
