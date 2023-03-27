package com.craftgate.restaurant.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantMenuItemDto {

    private String menuItemName;
    private Long menuItemId;
    private BigDecimal price;
    private Boolean available;
}
