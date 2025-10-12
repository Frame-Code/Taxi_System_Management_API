package io.github.frame_code.domain.entities;

import Enums.entitiesEnums.STATUS_ROAD;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Builder
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RideStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private STATUS_ROAD statusRoad;

    @Column(nullable = false)
    private int statusOrder;
}
