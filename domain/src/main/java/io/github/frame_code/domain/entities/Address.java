package io.github.frame_code.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import lombok.experimental.SuperBuilder;
import org.locationtech.jts.geom.Point;

@SuperBuilder
@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Address {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCity")
    private City city;

    @Column(columnDefinition = "POINT")
    private Point location;

    private String reference;
}
