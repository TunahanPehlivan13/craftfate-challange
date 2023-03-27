package com.craftgate.restaurant.service;

import com.craftgate.restaurant.entity.MenuItem;
import com.craftgate.restaurant.entity.Restaurant;
import com.craftgate.restaurant.entity.RestaurantGroup;
import com.craftgate.restaurant.entity.RestaurantSpecificDetails;
import com.craftgate.restaurant.exception.NoAvailableRestaurantGroupException;
import com.craftgate.restaurant.model.RestaurantMenuItem;
import com.craftgate.restaurant.repository.MenuItemRepository;
import com.craftgate.restaurant.repository.RestaurantGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantGroupRepository restaurantGroupRepository;
    private final EntityManager entityManager;

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
        return entityManager.createQuery("SELECT new com.craftgate.restaurant.model.RestaurantMenuItem(mi.name, mi.defaultPrice, rsd.price, rsd.available) " +
                        "FROM MenuItem mi " +
                        "LEFT JOIN RestaurantSpecificDetails rsd ON rsd.menuItem=mi AND rsd.restaurant.id=:restaurantId " +
                        "WHERE mi.restaurantGroup.id=:restaurantGroupId")
                .setParameter("restaurantId", restaurantId)
                .setParameter("restaurantGroupId", restaurantGroupId)
                .getResultList();
    }
}
