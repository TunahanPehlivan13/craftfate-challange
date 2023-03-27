package com.craftgate.restaurant.converter;

import com.craftgate.restaurant.dto.RestaurantGroupDto;
import com.craftgate.restaurant.entity.RestaurantGroup;
import org.springframework.stereotype.Component;

@Component
public class RestaurantGroupConverter {

    public RestaurantGroup to(RestaurantGroupDto restaurantGroupDto) {
        RestaurantGroup restaurantGroup = new RestaurantGroup();
        restaurantGroup.setName(restaurantGroupDto.getName());
        return restaurantGroup;
    }
}
