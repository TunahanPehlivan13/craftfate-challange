package com.craftgate.order.exception;

import com.craftgate.order.dto.StatusDto;

public class MismatchedPriceException extends PaymentValidationException {

    public MismatchedPriceException(StatusDto statusDto) {
        super(statusDto);
    }
}
