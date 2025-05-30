package io.github.frame_code.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter @Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaxiLiveAddress extends Address {
    @OneToOne
    @JoinColumn(name = "idTaxi", unique = true)
    private Taxi taxi;
}
