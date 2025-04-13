package entities;

import java.time.LocalDate;

public class Fare {
    private Long id;
    private Double pricePerMinute;
    private Double pricePerKm;
    private Double baseFare;
    private boolean isDeleted;
    private LocalDate createdAt;
}
