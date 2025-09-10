package io.github.frame_code.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter @Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Fare {
    @Id
    private final Integer id = 1;

    @Column(nullable = false)
    private Double pricePerMinute;

    @Column(nullable = false)
    private Double pricePerKm;

    @Column(nullable = false)
    private Double baseFare;

    @Column(nullable = false)
    private LocalDate createdAt;

    @PrePersist
    private void load() {
        createdAt = LocalDate.now();
    }

}
