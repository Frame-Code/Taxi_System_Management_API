package io.github.frame_code.domain.entities;

import Enums.entitiesEnums.PAYMENT_METHOD;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter @Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PAYMENT_METHOD paymentMethod;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double amount;

    @Column(nullable = false)
    private UUID transaction_code;

    @Column(nullable = false)
    private boolean isDeleted;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false, length = 50)
    private String createdBy;

    @PrePersist
    private void load() {
        createdAt = LocalDate.now();
        isDeleted = false;
    }

}
