package com.craftgate.restaurant.repository;

import com.craftgate.restaurant.entity.RestaurantSpecificDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantSpecificDetailsRepository extends JpaRepository<RestaurantSpecificDetails, Long> {
}
