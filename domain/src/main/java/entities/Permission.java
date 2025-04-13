package entities;

import Enums.PERMISSION_NAME;

import java.time.LocalDate;

public class Permission {
    private Long id;
    private PERMISSION_NAME permissionName;
    private LocalDate createdAt;
    private boolean isDeleted;
}
