package entities;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Builder
@Getter
@Setter
public class Province {
    private Long id;
    private String name;

    @OneToMany(mappedBy = "province")
    private Set<City> cities;
}
