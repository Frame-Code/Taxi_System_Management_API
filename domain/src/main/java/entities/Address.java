package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Address {
    private Long id;
    private String reference;
    private City city;
    private Double latitude;
    private Double longitude;
}
