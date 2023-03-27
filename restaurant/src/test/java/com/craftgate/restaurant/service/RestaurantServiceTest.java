package com.craftgate.restaurant.service;

import com.craftgate.restaurant.entity.Restaurant;
import com.craftgate.restaurant.entity.RestaurantGroup;
import com.craftgate.restaurant.exception.NoAvailableRestaurantGroupException;
import com.craftgate.restaurant.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @InjectMocks
    RestaurantService restaurantService;

    @Mock
    RestaurantGroupService restaurantGroupService;

    @Mock
    RestaurantRepository restaurantRepository;

    @Test
    public void shouldThrowsExceptionIfCouldNotFindRestaurantGroup() {
        Restaurant restaurant = new Restaurant();

        when(restaurantGroupService.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NoAvailableRestaurantGroupException.class, () ->
                restaurantService.save(1L, restaurant));
    }

    @Test
    void shouldSaveRestaurantWhenAllInputsCorrect() {
        Restaurant restaurant = new Restaurant();
        RestaurantGroup restaurantGroup = new RestaurantGroup();

        when(restaurantGroupService.findById(1L)).thenReturn(Optional.of(restaurantGroup));

        restaurantService.save(1L, restaurant);

        verify(restaurantRepository).save(restaurant);
    }
}
