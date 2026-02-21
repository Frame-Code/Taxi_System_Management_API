package dto.http.response;

import Enums.entitiesEnums.PAYMENT_METHOD;

public record SavePaymentResponseDto(
        Long id,
        PAYMENT_METHOD paymentMethod,
        Double amount
) {
}
