package com.craftgate.restaurant.service;

import com.craftgate.restaurant.entity.MenuItem;
import com.craftgate.restaurant.entity.Restaurant;
import com.craftgate.restaurant.entity.RestaurantSpecificDetails;
import com.craftgate.restaurant.exception.NoAvailableMenuItemException;
import com.craftgate.restaurant.exception.NoAvailableRestaurantException;
import com.craftgate.restaurant.exception.NoAvailableRestaurantGroupException;
import com.craftgate.restaurant.repository.RestaurantSpecificDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantSpecificDetailsService {

    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final RestaurantSpecificDetailsRepository restaurantSpecificDetailsRepository;
    private final RestaurantGroupService restaurantGroupService;

    public RestaurantSpecificDetails save(Long restaurantId,
                                          Long menuItemId,
                                          Long restaurantGroupId,
                                          RestaurantSpecificDetails restaurantSpecificDetails) {
        if (restaurantGroupService.findById(restaurantGroupId).isEmpty()) {
            throw new NoAvailableRestaurantGroupException();
        }
        Optional<Restaurant> mayRestaurant = restaurantService.findById(restaurantId);
        restaurantSpecificDetails.setRestaurant(mayRestaurant.orElseThrow(NoAvailableRestaurantException::new));

        Optional<MenuItem> mayMenuItem = menuItemService.findById(menuItemId);
        restaurantSpecificDetails.setMenuItem(mayMenuItem.orElseThrow(NoAvailableMenuItemException::new));

        return restaurantSpecificDetailsRepository.save(restaurantSpecificDetails);
    }
}
