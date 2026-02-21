package dto.http.request;

import Enums.entitiesEnums.PAYMENT_METHOD;

public record SavePaymentRequestDto(
        PAYMENT_METHOD paymentMethod,
        Double amount
) { }
