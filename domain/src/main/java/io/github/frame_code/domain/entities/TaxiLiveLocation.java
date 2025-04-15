package io.github.frame_code.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
@Entity
public class TaxiLiveLocation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "POINT")
    private Point location;

    @Column(columnDefinition = "DATETIME(6)")
    private LocalDateTime timeStamp;

    @ManyToOne
    @JoinColumn(name = "idTaxi")
    private Taxi taxi;
}
