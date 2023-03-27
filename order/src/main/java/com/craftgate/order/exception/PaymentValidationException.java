package com.craftgate.order.exception;

import com.craftgate.order.dto.StatusDto;

public abstract class PaymentValidationException extends RuntimeException {

    private StatusDto statusDto;

    public PaymentValidationException(StatusDto statusDto) {
        this.statusDto = statusDto;
    }

    public StatusDto getStatusDto() {
        return statusDto;
    }

    public void setStatusDto(StatusDto statusDto) {
        this.statusDto = statusDto;
    }
}
