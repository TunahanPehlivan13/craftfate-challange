package com.craftgate.restaurant.converter;

import com.craftgate.restaurant.dto.RestaurantDto;
import com.craftgate.restaurant.entity.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantConverter {

    public Restaurant to(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        return restaurant;
    }
}
