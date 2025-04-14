package entities;

import Enums.entitiesEnums.STATUS_ROAD;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter@Setter
public class Road {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Address startAddress;
    private Address endAddress;
    private STATUS_ROAD status;
    private String message;
    private LocalDate createdAt;
    private String createdBy;
    private LocalDate updatedAt;
    private String updatedBy;
    private boolean isDeleted;
}
