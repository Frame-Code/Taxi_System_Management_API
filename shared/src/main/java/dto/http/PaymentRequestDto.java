package dto.http;

import Enums.entitiesEnums.PAYMENT_METHOD;

public record PaymentRequestDto(
        PAYMENT_METHOD paymentMethod,
        Double amount
) { }
