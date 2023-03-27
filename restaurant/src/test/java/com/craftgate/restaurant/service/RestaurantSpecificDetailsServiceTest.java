package com.craftgate.restaurant.service;

import com.craftgate.restaurant.entity.MenuItem;
import com.craftgate.restaurant.entity.Restaurant;
import com.craftgate.restaurant.entity.RestaurantGroup;
import com.craftgate.restaurant.entity.RestaurantSpecificDetails;
import com.craftgate.restaurant.exception.NoAvailableMenuItemException;
import com.craftgate.restaurant.exception.NoAvailableRestaurantException;
import com.craftgate.restaurant.exception.NoAvailableRestaurantGroupException;
import com.craftgate.restaurant.repository.RestaurantSpecificDetailsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantSpecificDetailsServiceTest {

    @InjectMocks
    RestaurantSpecificDetailsService restaurantSpecificDetailsService;

    @Mock
    RestaurantGroupService restaurantGroupService;

    @Mock
    RestaurantService restaurantService;

    @Mock
    MenuItemService menuItemService;

    @Mock
    RestaurantSpecificDetailsRepository restaurantSpecificDetailsRepository;

    @Test
    public void shouldThrowExceptionIfCouldNotFindRestaurantGroup() {
        RestaurantSpecificDetails restaurantSpecificDetails = new RestaurantSpecificDetails();

        when(restaurantGroupService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoAvailableRestaurantGroupException.class, () -> {
            restaurantSpecificDetailsService.save(1L, 1L, 1L, restaurantSpecificDetails);
        });
    }

    @Test
    void shouldThrowExceptionIfCouldNotFindRestaurant() {
        RestaurantSpecificDetails restaurantSpecificDetails = new RestaurantSpecificDetails();

        RestaurantGroup restaurantGroup = new RestaurantGroup();

        when(restaurantGroupService.findById(1L)).thenReturn(Optional.of(restaurantGroup));
        when(restaurantService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoAvailableRestaurantException.class, () ->
                restaurantSpecificDetailsService.save(1L, 1L, 1L, restaurantSpecificDetails));
    }

    @Test
    void shouldThrowExceptionIfCouldNotFindMenuItem() {
        RestaurantSpecificDetails restaurantSpecificDetails = new RestaurantSpecificDetails();

        RestaurantGroup restaurantGroup = new RestaurantGroup();
        Restaurant restaurant = new Restaurant();

        when(restaurantGroupService.findById(1L)).thenReturn(Optional.of(restaurantGroup));
        when(restaurantService.findById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoAvailableMenuItemException.class, () ->
                restaurantSpecificDetailsService.save(1L, 1L, 1L, restaurantSpecificDetails));
    }

    @Test
    void shouldSaveWhenAllInputsCorrect() {
        RestaurantSpecificDetails restaurantSpecificDetails = new RestaurantSpecificDetails();

        RestaurantGroup restaurantGroup = new RestaurantGroup();
        Restaurant restaurant = new Restaurant();
        MenuItem menuItem = new MenuItem();

        when(restaurantGroupService.findById(1L)).thenReturn(Optional.of(restaurantGroup));
        when(restaurantService.findById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemService.findById(1L)).thenReturn(Optional.of(menuItem));

        restaurantSpecificDetailsService.save(1L, 1L, 1L, restaurantSpecificDetails);

        verify(restaurantSpecificDetailsRepository).save(restaurantSpecificDetails);
    }
}
