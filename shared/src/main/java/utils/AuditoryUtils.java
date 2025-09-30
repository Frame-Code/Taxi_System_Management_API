package utils;

import dto.UserAuditoryDTO;

public class AuditoryUtils {
    public static String createCreatedAt(UserAuditoryDTO userAuditoryDTO) {
        return "User name: " + userAuditoryDTO.userFullNames() + ". Rol: " + userAuditoryDTO.userRoleName();
    }
}
