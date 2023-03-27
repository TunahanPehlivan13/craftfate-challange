package com.craftgate.restaurant.controller;

import com.craftgate.restaurant.converter.RestaurantGroupConverter;
import com.craftgate.restaurant.dto.RestaurantGroupDto;
import com.craftgate.restaurant.entity.RestaurantGroup;
import com.craftgate.restaurant.service.RestaurantGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/restaurantGroups")
@RequiredArgsConstructor
public class RestaurantGroupController {

    private final RestaurantGroupConverter restaurantGroupConverter;
    private final RestaurantGroupService restaurantGroupService;

    @PostMapping
    public ResponseEntity post(@RequestBody RestaurantGroupDto restaurantGroupDto) {
        RestaurantGroup restaurantGroup = restaurantGroupConverter.to(restaurantGroupDto);
        Long restaurantGroupId = restaurantGroupService.save(restaurantGroup).getId();

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(restaurantGroupId)
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
