package io.github.frame_code.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

/**
 * Entidad que representa un usuario en el sistema
 */
@Getter @Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@ToString
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 60, nullable = false)
    private String names;

    @Column(length = 60, nullable = false)
    private String lastNames;

    @Column(length = 80, nullable = false, unique = true)
    private String email;

    @Column(length = 15, nullable = false, unique = true)
    private String phone;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String passwordHash;

    @Column(length = 255)
    private String photo;

    @Column(nullable = false)
    private LocalDate bornDate;

    @ManyToOne
    @JoinColumn(name = "idRole")
    private Role role;

    /**
     * Retorna el nombre completo del usuario
     * @return Nombre completo concatenado
     */
    public String getFullNames() {
        return names + " " + lastNames;
    }
    public String getAge() {
        return String.valueOf(Period.between(bornDate, LocalDate.now()).getYears());
    }

}
