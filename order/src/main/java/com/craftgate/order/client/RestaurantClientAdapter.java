package com.craftgate.order.client;

import com.craftgate.order.client.dto.RestaurantMenuItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "categoryClient", url = "${restaurant.service.url}")
public interface RestaurantClientAdapter {

    @RequestMapping(method = RequestMethod.GET, value = "/restaurantGroups/{restaurantGroupId}/restaurants/{restaurantId}/menuItems")
    Iterable<RestaurantMenuItemDto> getRestaurantMenuItem(@PathVariable Long restaurantGroupId, @PathVariable Long restaurantId);
}
