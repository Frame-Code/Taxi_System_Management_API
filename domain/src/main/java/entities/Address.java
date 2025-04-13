package entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Builder
@Getter @Setter
@Entity
public class Address {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String reference;

    @OneToOne
    private City city;

    @Column(columnDefinition = "POINT")
    private Point location;
}
