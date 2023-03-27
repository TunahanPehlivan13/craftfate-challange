package com.craftgate.restaurant.controller;

import com.craftgate.restaurant.converter.MenuItemConverter;
import com.craftgate.restaurant.converter.RestaurantMenuItemConverter;
import com.craftgate.restaurant.dto.MenuItemDto;
import com.craftgate.restaurant.dto.RestaurantMenuItemDto;
import com.craftgate.restaurant.entity.MenuItem;
import com.craftgate.restaurant.model.RestaurantMenuItem;
import com.craftgate.restaurant.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/restaurantGroups/{restaurantGroupId}")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;
    private final MenuItemConverter menuItemConverter;
    private final RestaurantMenuItemConverter restaurantMenuItemConverter;

    @PostMapping("/menuItems")
    public ResponseEntity createAll(@PathVariable Long restaurantGroupId, @RequestBody List<MenuItemDto> menuItemDtos) {
        List<MenuItem> menuItems = menuItemConverter.to(menuItemDtos);
        menuItemService.saveAll(restaurantGroupId, menuItems);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/restaurants/{restaurantId}/menuItems")
    public ResponseEntity getMenuItemsByRestaurant(@PathVariable Long restaurantGroupId, @PathVariable Long restaurantId) {
        List<RestaurantMenuItem> restaurantMenuItems = menuItemService.getMenuItemsByRestaurant(restaurantGroupId, restaurantId);
        List<RestaurantMenuItemDto> items = restaurantMenuItemConverter.to(restaurantMenuItems);
        return ResponseEntity.ok(items);
    }
}
