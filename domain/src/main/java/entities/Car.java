package entities;

import lombok.Builder;

@Builder
public class Car extends Vehicle{
    private String licensePlate;
    private String chasisNumber;

}
