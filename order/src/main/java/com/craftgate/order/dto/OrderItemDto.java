package com.craftgate.order.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemDto {

    @NotNull
    private BigDecimal price;

    @NotNull
    private Long menuItemId;
}
