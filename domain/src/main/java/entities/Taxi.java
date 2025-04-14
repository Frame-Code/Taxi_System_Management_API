package entities;

import Enums.entitiesEnums.STATUS_TAXI;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class Taxi {
    private Long id;
    private Vehicle vehicle;
    private Driver driver;
    private STATUS_TAXI status;
    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updatedBy;
    private boolean isDeleted;

}
