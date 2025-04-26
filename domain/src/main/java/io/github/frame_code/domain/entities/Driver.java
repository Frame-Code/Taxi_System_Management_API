package io.github.frame_code.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

/**
 * Entidad que representa un conductor en el sistema
 */
@Getter @Setter
@NoArgsConstructor
@SuperBuilder
@Entity
@ToString
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUser")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idLicense")
    private License license;

    @Column(nullable = false, length = 150)
    private String address;

    @Column(nullable = false)
    private LocalDate entryDate;

    private Integer experienceYears;
}

