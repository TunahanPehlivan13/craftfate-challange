package com.craftgate.order.exception;

import com.craftgate.order.dto.StatusDto;

public class NoValidMenuItemException extends PaymentValidationException {

    public NoValidMenuItemException(StatusDto statusDto) {
        super(statusDto);
    }
}
