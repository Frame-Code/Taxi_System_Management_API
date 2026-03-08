package dto.entities;

import Enums.entitiesEnums.PAYMENT_METHOD;

public record PaymentDTO(
        PAYMENT_METHOD paymentMethod,
        Double amount
) {
}
