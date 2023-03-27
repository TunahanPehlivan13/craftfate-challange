package com.craftgate.restaurant.service;

import com.craftgate.restaurant.entity.Restaurant;
import com.craftgate.restaurant.entity.RestaurantGroup;
import com.craftgate.restaurant.exception.NoAvailableRestaurantGroupException;
import com.craftgate.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantGroupService restaurantGroupService;

    public Restaurant save(Long restaurantGroupId, Restaurant restaurant) {
        RestaurantGroup restaurantGroup = restaurantGroupService.findById(restaurantGroupId)
                .orElseThrow(NoAvailableRestaurantGroupException::new);
        restaurant.setRestaurantGroup(restaurantGroup);
        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> findById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }
}
