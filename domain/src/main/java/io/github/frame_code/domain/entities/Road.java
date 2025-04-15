package io.github.frame_code.domain.entities;

import Enums.entitiesEnums.STATUS_ROAD;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
@Entity
public class Road {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "idAddressOrigin")
    private Address startAddress;

    @ManyToOne
    @JoinColumn(name = "idAddressDestiny")
    private Address endAddress;

    @Enumerated
    @Column(nullable = false)
    private STATUS_ROAD status;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false, length = 60)
    private String createdBy;

    @Column(nullable = false)
    private LocalDate updatedAt;

    @Column(nullable = false, length = 60)
    private String updatedBy;

    @Column(nullable = false)
    private boolean isDeleted;

    @PrePersist
    private void load() {
        createdAt = LocalDate.now();
        isDeleted = false;
    }

    @PreUpdate
    private void update() {
        updatedAt = LocalDate.now();
    }
}
