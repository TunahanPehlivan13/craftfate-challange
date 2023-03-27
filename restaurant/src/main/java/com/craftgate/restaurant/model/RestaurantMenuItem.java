package com.craftgate.restaurant.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantMenuItem {

    private String menuItemName;
    private BigDecimal defaultPrice;
    private BigDecimal restaurantSpecificPrice;
    private Boolean available;
}
