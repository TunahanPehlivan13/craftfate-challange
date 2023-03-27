package com.craftgate.restaurant.repository;

import com.craftgate.restaurant.entity.RestaurantGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantGroupRepository extends JpaRepository<RestaurantGroup, Long> {
}
