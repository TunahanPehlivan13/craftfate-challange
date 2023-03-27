package com.craftgate.restaurant.dao;

import com.craftgate.restaurant.entity.MenuItem;
import com.craftgate.restaurant.entity.Restaurant;
import com.craftgate.restaurant.entity.RestaurantGroup;
import com.craftgate.restaurant.entity.RestaurantSpecificDetails;
import com.craftgate.restaurant.model.RestaurantMenuItem;
import com.craftgate.restaurant.repository.MenuItemRepository;
import com.craftgate.restaurant.repository.RestaurantGroupRepository;
import com.craftgate.restaurant.repository.RestaurantRepository;
import com.craftgate.restaurant.repository.RestaurantSpecificDetailsRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantMenuItemDaoIntegrationTest {

    @Autowired
    RestaurantMenuItemDao restaurantMenuItemDao;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantGroupRepository restaurantGroupRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    RestaurantSpecificDetailsRepository restaurantSpecificDetailsRepository;

    @Before
    public void before() {
        RestaurantGroup restaurantGroup = new RestaurantGroup();
        restaurantGroup.setName("resgroup");
        restaurantGroupRepository.save(restaurantGroup);

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setRestaurantGroup(restaurantGroup);
        restaurant1.setName("restaurant1");

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setRestaurantGroup(restaurantGroup);
        restaurant2.setName("restaurant2");

        restaurantRepository.saveAll(Lists.newArrayList(restaurant1, restaurant2));

        MenuItem menuItem1 = new MenuItem();
        menuItem1.setRestaurantGroup(restaurantGroup);
        menuItem1.setDefaultPrice(BigDecimal.ONE);
        menuItem1.setName("menuItem1");

        MenuItem menuItem2 = new MenuItem();
        menuItem2.setRestaurantGroup(restaurantGroup);
        menuItem2.setDefaultPrice(BigDecimal.TEN);
        menuItem2.setName("menuItem2");

        MenuItem menuItem3 = new MenuItem();
        menuItem3.setRestaurantGroup(restaurantGroup);
        menuItem3.setDefaultPrice(BigDecimal.valueOf(3));
        menuItem3.setName("menuItem3");

        menuItemRepository.saveAll(Lists.newArrayList(menuItem1, menuItem2, menuItem3));

        RestaurantSpecificDetails restaurantSpecificDetails1 = new RestaurantSpecificDetails();
        restaurantSpecificDetails1.setRestaurant(restaurant1);
        restaurantSpecificDetails1.setAvailable(true);
        restaurantSpecificDetails1.setPrice(BigDecimal.valueOf(5));
        restaurantSpecificDetails1.setMenuItem(menuItem1);

        RestaurantSpecificDetails restaurantSpecificDetails2 = new RestaurantSpecificDetails();
        restaurantSpecificDetails2.setRestaurant(restaurant1);
        restaurantSpecificDetails2.setAvailable(false);
        restaurantSpecificDetails2.setPrice(null);
        restaurantSpecificDetails2.setMenuItem(menuItem2);

        restaurantSpecificDetailsRepository
                .saveAll(Lists.newArrayList(restaurantSpecificDetails1, restaurantSpecificDetails2));
    }

    @Test
    public void shouldGetRestaurantMenuItem() {
        List<RestaurantMenuItem> restaurantMenuItems = restaurantMenuItemDao
                .getMenuItemsByRestaurant(1L, 2L);

        assertThat(restaurantMenuItems, hasSize(3));

        RestaurantMenuItem restaurantMenuItem1 = restaurantMenuItems.get(0);
        assertEquals("menuItem1", restaurantMenuItem1.getMenuItemName());
        assertNotNull(restaurantMenuItem1.getMenuItemId());
        assertTrue(restaurantMenuItem1.getDefaultPrice().compareTo(BigDecimal.ONE) == 0);

        RestaurantMenuItem restaurantMenuItem2 = restaurantMenuItems.get(1);
        assertEquals("menuItem2", restaurantMenuItem2.getMenuItemName());
        assertNotNull(restaurantMenuItem2.getMenuItemId());
        assertTrue(restaurantMenuItem2.getDefaultPrice().compareTo(BigDecimal.TEN) == 0);

        RestaurantMenuItem restaurantMenuItem3 = restaurantMenuItems.get(2);
        assertEquals("menuItem3", restaurantMenuItem3.getMenuItemName());
        assertNotNull(restaurantMenuItem3.getMenuItemId());
        assertTrue(restaurantMenuItem3.getDefaultPrice().compareTo(BigDecimal.valueOf(3)) == 0);
    }

    @Test
    public void shouldGetNotAvailableRestaurantMenuItem() {
        List<RestaurantMenuItem> restaurantMenuItems = restaurantMenuItemDao
                .getMenuItemsByRestaurant(1L, 2L);

        assertThat(restaurantMenuItems, hasSize(3));

        RestaurantMenuItem restaurantMenuItem1 = restaurantMenuItems.get(0);
        assertTrue(restaurantMenuItem1.getAvailable());

        RestaurantMenuItem restaurantMenuItem2 = restaurantMenuItems.get(1);
        assertFalse(restaurantMenuItem2.getAvailable());

        RestaurantMenuItem restaurantMenuItem3 = restaurantMenuItems.get(2);
        assertNull(restaurantMenuItem3.getAvailable());
    }

    @Test
    public void shouldGetOverridePriceRestaurantMenuItem() {
        List<RestaurantMenuItem> restaurantMenuItems = restaurantMenuItemDao
                .getMenuItemsByRestaurant(1L, 2L);

        assertThat(restaurantMenuItems, hasSize(3));

        RestaurantMenuItem restaurantMenuItem1 = restaurantMenuItems.get(0);
        assertTrue(restaurantMenuItem1.getRestaurantSpecificPrice().compareTo(BigDecimal.valueOf(5d)) == 0);

        RestaurantMenuItem restaurantMenuItem2 = restaurantMenuItems.get(1);
        assertNull(restaurantMenuItem2.getRestaurantSpecificPrice());

        RestaurantMenuItem restaurantMenuItem3 = restaurantMenuItems.get(2);
        assertNull(restaurantMenuItem3.getRestaurantSpecificPrice());
    }
}
