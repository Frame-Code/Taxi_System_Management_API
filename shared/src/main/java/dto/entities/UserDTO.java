package dto.entities;

import java.time.LocalDate;

public record UserDTO(
        String names,
        String lastNames,
        LocalDate bornDate,
        String email,
        String age
) {
    public String getInfo() {
        return names + " " + lastNames + ", email: " + email;
    }

    public String getFullNames() {
        return names + " " + lastNames;
    }
}
