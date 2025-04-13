package entities;

import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String name;
    private String lastNames;
    private LocalDate bornDate;
    private String email;
    private String phone;
    private String passwordHash;
    private String photo;
    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updatedBy;
    private boolean isDeleted;

    public String getFullNames() {return name + " " + lastNames;}

    private void prePersist() {
        createdAt = LocalDate.now();
        isDeleted = false;
    }
}
