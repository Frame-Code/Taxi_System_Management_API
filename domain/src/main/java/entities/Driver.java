package entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver extends User{
    private Long id;
    private String address;
    private Integer experienceYears;
    private LocalDate entryDate;

}

