package entities;

import Enums.PAYMENT_METHOD;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Getter@Setter
public class Payment {
    private Long id;
    private PAYMENT_METHOD paymentMethod;
    private Double amount;
    private UUID transaction_code;
    private boolean isDeleted;
    private LocalDate createdAt;
    private String createdBy;

}
