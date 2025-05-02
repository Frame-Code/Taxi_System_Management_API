package io.github.frame_code.domain.entities;

import Enums.entitiesEnums.REQUEST_STATUS;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
@Entity
public class RoadNotification extends Notification{
    @ManyToOne
    @JoinColumn(name = "idDriver")
    private Driver driver;

    @ManyToOne
    @JoinColumn(name = "idClient")
    private Client client;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private REQUEST_STATUS status;
}
