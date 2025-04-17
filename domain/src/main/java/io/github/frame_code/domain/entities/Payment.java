package io.github.frame_code.domain.entities;

import Enums.entitiesEnums.PAYMENT_METHOD;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Entidad que representa un pago en el sistema
 */
@Getter @Setter
@NoArgsConstructor
@SuperBuilder
@Entity
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PAYMENT_METHOD paymentMethod;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private UUID transactionCode;
}
