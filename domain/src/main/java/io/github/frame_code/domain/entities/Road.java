package io.github.frame_code.domain.entities;

import jakarta.persistence.*;
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

    @Column()
    private LocalDateTime endDate;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idAddressOrigin")
    private Address startAddress;

    @ManyToOne(cascade = CascadeType.PERSIST)
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
