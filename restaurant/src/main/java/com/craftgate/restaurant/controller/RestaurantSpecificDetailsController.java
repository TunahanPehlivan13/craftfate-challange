package com.craftgate.restaurant.controller;

import com.craftgate.restaurant.converter.RestaurantSpecificDetailsConverter;
import com.craftgate.restaurant.dto.RestaurantSpecificDetailsDto;
import com.craftgate.restaurant.entity.RestaurantSpecificDetails;
import com.craftgate.restaurant.service.RestaurantSpecificDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurantGroups/{restaurantGroupId}/restaurants/{restaurantId}/menuItems/{menuItemId}")
@RequiredArgsConstructor
public class RestaurantSpecificDetailsController {

    private final RestaurantSpecificDetailsService restaurantSpecificDetailsService;
    private final RestaurantSpecificDetailsConverter restaurantSpecificDetailsConverter;

    @PostMapping
    public ResponseEntity post(@PathVariable Long menuItemId,
                               @PathVariable Long restaurantGroupId,
                               @PathVariable Long restaurantId,
                               @RequestBody RestaurantSpecificDetailsDto restaurantSpecificDetailsDto) {

        RestaurantSpecificDetails restaurantSpecificDetails = restaurantSpecificDetailsConverter.to(restaurantSpecificDetailsDto);
        restaurantSpecificDetailsService.save(restaurantId, menuItemId, restaurantGroupId, restaurantSpecificDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
