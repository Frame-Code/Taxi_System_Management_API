package io.github.frame_code.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter @Setter
@Entity
public class Car extends Vehicle{
    @Column(length = 15, unique = true, nullable = false)
    private String licensePlate;

    @Column(length = 25, unique = true, nullable = false)
    private String chassisNumber;

}
