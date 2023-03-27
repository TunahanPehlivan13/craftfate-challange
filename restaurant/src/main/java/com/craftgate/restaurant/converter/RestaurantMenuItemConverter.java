package com.craftgate.restaurant.converter;

import com.craftgate.restaurant.dto.RestaurantMenuItemDto;
import com.craftgate.restaurant.model.RestaurantMenuItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantMenuItemConverter {

    public RestaurantMenuItemDto to(RestaurantMenuItem restaurantMenuItem) {
        RestaurantMenuItemDto restaurantMenuItemDto = new RestaurantMenuItemDto();
        restaurantMenuItemDto.setMenuItemName(restaurantMenuItem.getMenuItemName());

        Boolean available = restaurantMenuItem.getAvailable() == null ? Boolean.TRUE : restaurantMenuItem.getAvailable();
        restaurantMenuItemDto.setAvailable(available);

        BigDecimal price = restaurantMenuItem.getRestaurantSpecificPrice() == null
                ? restaurantMenuItem.getDefaultPrice()
                : restaurantMenuItem.getRestaurantSpecificPrice();
        restaurantMenuItemDto.setPrice(price);

        return restaurantMenuItemDto;
    }

    public List<RestaurantMenuItemDto> to(List<RestaurantMenuItem> restaurantMenuItems) {
        List<RestaurantMenuItemDto> restaurantMenuItemDtos = new ArrayList<>();
        for (RestaurantMenuItem restaurantMenuItem : restaurantMenuItems) {
            restaurantMenuItemDtos.add(to(restaurantMenuItem));
        }
        return restaurantMenuItemDtos;
    }
}
