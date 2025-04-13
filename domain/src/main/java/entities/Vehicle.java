package entities;

import lombok.Builder;

@Builder
public abstract class Vehicle {
    private Long id;
    private String photo;
    private String year;
    private String color;
    private String brand;
    private String model;
}
