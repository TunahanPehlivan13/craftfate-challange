package com.craftgate.restaurant.dao;

import com.craftgate.restaurant.model.RestaurantMenuItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RestaurantMenuItemDao {

    private final EntityManager entityManager;

    public List<RestaurantMenuItem> getMenuItemsByRestaurant(Long restaurantGroupId, Long restaurantId) {
        return entityManager.createQuery("SELECT new com.craftgate.restaurant.model.RestaurantMenuItem(mi.name, mi.id, mi.defaultPrice, rsd.price, rsd.available) " +
                        "FROM MenuItem mi " +
                        "LEFT JOIN RestaurantSpecificDetails rsd ON rsd.menuItem=mi AND rsd.restaurant.id=:restaurantId " +
                        "WHERE mi.restaurantGroup.id=:restaurantGroupId")
                .setParameter("restaurantId", restaurantId)
                .setParameter("restaurantGroupId", restaurantGroupId)
                .getResultList();

    }
}
