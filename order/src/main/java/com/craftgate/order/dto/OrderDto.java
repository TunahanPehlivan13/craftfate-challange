package com.craftgate.order.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class OrderDto {

    @NotNull
    private Long restaurantGroupId;

    @NotNull
    private Long restaurantId;

    @Valid
    private List<OrderItemDto> orderItems;
}
