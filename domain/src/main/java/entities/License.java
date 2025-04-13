package entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter@Setter
public class License {
    private Long id;
    private String driverLicense;
    private Integer licenseType;
    private LocalDate issuanceDate;
    private LocalDate expirationDate;
}
