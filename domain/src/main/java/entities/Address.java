package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Builder
@Getter
@Setter
public class Address {
    private Long id;
    private String reference;
    private City city;
    private Point location;
}
