package com.craftgate.restaurant.service;

import com.craftgate.restaurant.dao.RestaurantMenuItemDao;
import com.craftgate.restaurant.entity.MenuItem;
import com.craftgate.restaurant.entity.RestaurantGroup;
import com.craftgate.restaurant.exception.NoAvailableRestaurantGroupException;
import com.craftgate.restaurant.model.RestaurantMenuItem;
import com.craftgate.restaurant.repository.MenuItemRepository;
import com.craftgate.restaurant.repository.RestaurantGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantGroupRepository restaurantGroupRepository;
    private final RestaurantMenuItemDao restaurantMenuItemDao;

    public void saveAll(Long restaurantGroupId, List<MenuItem> menuItems) {
        Optional<RestaurantGroup> mayRestaurantGroup = restaurantGroupRepository.findById(restaurantGroupId);
        RestaurantGroup restaurantGroup = mayRestaurantGroup.orElseThrow(NoAvailableRestaurantGroupException::new);
        menuItems.forEach(menuItem -> menuItem.setRestaurantGroup(restaurantGroup));
        menuItemRepository.saveAll(menuItems);
    }

    public Optional<MenuItem> findById(Long menuItemId) {
        return menuItemRepository.findById(menuItemId);
    }

    public List<RestaurantMenuItem> getMenuItemsByRestaurant(Long restaurantGroupId, Long restaurantId) {
        return restaurantMenuItemDao.getMenuItemsByRestaurant(restaurantGroupId, restaurantId);
    }
}
