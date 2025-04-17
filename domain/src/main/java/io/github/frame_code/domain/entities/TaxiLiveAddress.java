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
import lombok.experimental.SuperBuilder;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;

@SuperBuilder
@Getter @Setter
@Entity
public class TaxiLiveAddress extends Address {
    @ManyToOne
    @JoinColumn(name = "idTaxi")
    private Taxi taxi;
}
