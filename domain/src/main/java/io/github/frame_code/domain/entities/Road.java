package io.github.frame_code.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

import Enums.entitiesEnums.STATUS_ROAD;

/**
 * Entidad que representa un viaje/ruta en el sistema
 */
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@Entity
public class Road extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "idAddressOrigin")
    private Address startAddress;

    @ManyToOne
    @JoinColumn(name = "idAddressDestiny")
    private Address endAddress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STATUS_ROAD status;

    @ManyToOne
    @JoinColumn(name = "idPayment")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "idTaxi")
    private Taxi taxi;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;
}
