package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class City {
    private Long id;
    private String name;
    private Province province;
}
