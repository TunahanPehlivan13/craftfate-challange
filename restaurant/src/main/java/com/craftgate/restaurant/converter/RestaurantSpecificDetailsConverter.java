package com.craftgate.restaurant.converter;

import com.craftgate.restaurant.dto.RestaurantSpecificDetailsDto;
import com.craftgate.restaurant.entity.RestaurantSpecificDetails;
import org.springframework.stereotype.Component;

@Component
public class RestaurantSpecificDetailsConverter {

    public RestaurantSpecificDetails to(RestaurantSpecificDetailsDto restaurantSpecificDetailsDto) {
        RestaurantSpecificDetails restaurantSpecificDetails = new RestaurantSpecificDetails();
        restaurantSpecificDetails.setPrice(restaurantSpecificDetailsDto.getPrice());
        restaurantSpecificDetails.setAvailable(restaurantSpecificDetailsDto.getAvailable());
        return restaurantSpecificDetails;
    }
}
