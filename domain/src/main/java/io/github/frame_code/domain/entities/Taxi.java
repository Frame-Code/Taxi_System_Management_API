package io.github.frame_code.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

import Enums.entitiesEnums.STATUS_TAXI;

/**
 * Entidad que representa un taxi en el sistema
 */
@Getter @Setter
@NoArgsConstructor
@SuperBuilder
@Entity
public class Taxi extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idVehicle", unique = true, nullable = false)
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "idDriver", unique = true, nullable = false)
    private Driver driver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STATUS_TAXI status;

    @OneToMany(mappedBy = "taxi")
    private List<TaxiLiveAddress> taxiLiveAddresses;
}
