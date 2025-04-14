package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter @Setter
@Entity
public class Fare {
    @Id
    private final Integer id = 1;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double pricePerMinute;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double pricePerKm;

    @Column(nullable = false, precision = 10, scale = 2)
    private Double baseFare;

    @Column(nullable = false)
    private LocalDate createdAt;

    @PrePersist
    private void load() {
        createdAt = LocalDate.now();
    }

}
