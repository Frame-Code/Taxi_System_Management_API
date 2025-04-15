package io.github.frame_code.domain.entities;

import Enums.entitiesEnums.STATUS_TAXI;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter @Setter
@Entity
public class Taxi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "idVehicle")
    private Vehicle vehicle;

    @OneToOne
    @JoinColumn(name = "idDriver")
    private Driver driver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STATUS_TAXI status;

    @OneToMany(mappedBy = "taxi")
    private List<TaxiLiveLocation> taxiLiveLocations;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(nullable = false, length = 50)
    private String createdBy;

    @Column(nullable = false)
    private LocalDate updatedAt;

    @Column(nullable = false, length = 50)
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
