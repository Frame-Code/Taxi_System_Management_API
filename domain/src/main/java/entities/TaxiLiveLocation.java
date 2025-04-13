package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class TaxiLiveLocation{
    private Long id;
    private Point location;
    private LocalDate timeStamp;
    private Taxi taxi;
}
