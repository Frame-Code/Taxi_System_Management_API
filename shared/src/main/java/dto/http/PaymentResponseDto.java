package dto.http;

import Enums.entitiesEnums.PAYMENT_METHOD;

public record PaymentResponseDto(
        Long id,
        PAYMENT_METHOD paymentMethod,
        Double amount
) {
}
