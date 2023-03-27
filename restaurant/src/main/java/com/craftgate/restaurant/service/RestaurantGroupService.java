package com.craftgate.restaurant.service;

import com.craftgate.restaurant.entity.RestaurantGroup;
import com.craftgate.restaurant.repository.RestaurantGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantGroupService {

    private final RestaurantGroupRepository restaurantGroupRepository;

    public Optional<RestaurantGroup> findById(Long id) {
        return restaurantGroupRepository.findById(id);
    }

    public RestaurantGroup save(RestaurantGroup restaurantGroup) {
        return restaurantGroupRepository.save(restaurantGroup);
    }
}
