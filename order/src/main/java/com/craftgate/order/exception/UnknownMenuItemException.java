package com.craftgate.order.exception;

import com.craftgate.order.dto.StatusDto;

public class UnknownMenuItemException extends PaymentValidationException {

    public UnknownMenuItemException(StatusDto statusDto) {
        super(statusDto);
    }
}
