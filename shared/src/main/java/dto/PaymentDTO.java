package dto;

import Enums.entitiesEnums.PAYMENT_METHOD;

public record PaymentDTO(
        Long id,
        PAYMENT_METHOD paymentMethod,
        Double amount
) {
}
