package com.craftgate.restaurant.controller;

import com.craftgate.restaurant.converter.RestaurantConverter;
import com.craftgate.restaurant.dto.RestaurantDto;
import com.craftgate.restaurant.entity.Restaurant;
import com.craftgate.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/restaurantGroups/{restaurantGroupId}/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantConverter restaurantConverter;

    @PostMapping
    public ResponseEntity post(@PathVariable Long restaurantGroupId, @RequestBody RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantConverter.to(restaurantDto);
        Long restaurantId = restaurantService.save(restaurantGroupId, restaurant).getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(restaurantId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
