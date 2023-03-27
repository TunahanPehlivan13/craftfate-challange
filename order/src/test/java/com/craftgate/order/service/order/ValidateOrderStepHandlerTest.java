package com.craftgate.order.service.order;

import com.craftgate.order.client.RestaurantClientAdapter;
import com.craftgate.order.client.dto.RestaurantMenuItemDto;
import com.craftgate.order.entitiy.OrderItem;
import com.craftgate.order.entitiy.Orders;
import com.craftgate.order.exception.MismatchedPriceException;
import com.craftgate.order.exception.NoValidMenuItemException;
import com.craftgate.order.exception.UnknownMenuItemException;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidateOrderStepHandlerTest {

    @InjectMocks
    private ValidateOrderStepHandler validateOrderStepHandler;

    @Mock
    private RestaurantClientAdapter restaurantClientAdapter;

    @Test
    public void shouldPassValidationWhenAllInputsCorrect() {
        Orders orders = new Orders();
        orders.setRestaurantId(1L);
        orders.setRestaurantGroupId(1L);

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItemId(1L);
        orderItem.setPrice(BigDecimal.TEN);

        orders.setOrderItems(Lists.newArrayList(orderItem));

        RestaurantMenuItemDto restaurantMenuItemDto = new RestaurantMenuItemDto();
        restaurantMenuItemDto.setMenuItemId(1L);
        restaurantMenuItemDto.setPrice(BigDecimal.TEN);
        restaurantMenuItemDto.setAvailable(true);

        when(restaurantClientAdapter.getRestaurantMenuItem(1L, 1L))
                .thenReturn(Lists.newArrayList(restaurantMenuItemDto));

        validateOrderStepHandler.process(orders);
    }

    @Test
    void shouldThrowsExceptionWhenMenuItemIsNotValid() {
        assertThrows(NoValidMenuItemException.class, () -> {
            Orders orders = new Orders();
            orders.setRestaurantId(1L);
            orders.setRestaurantGroupId(1L);

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(2L);
            orderItem.setPrice(BigDecimal.TEN);

            orders.setOrderItems(Lists.newArrayList(orderItem));

            RestaurantMenuItemDto restaurantMenuItemDto = new RestaurantMenuItemDto();
            restaurantMenuItemDto.setMenuItemId(1L);
            restaurantMenuItemDto.setPrice(BigDecimal.TEN);
            restaurantMenuItemDto.setAvailable(true);

            when(restaurantClientAdapter.getRestaurantMenuItem(1L, 1L))
                    .thenReturn(Lists.newArrayList(restaurantMenuItemDto));

            validateOrderStepHandler.process(orders);
        });
    }

    @Test
    void shouldThrowsExceptionWhenPriceIsNotMatched() {
        assertThrows(MismatchedPriceException.class, () -> {
            Orders orders = new Orders();
            orders.setRestaurantId(1L);
            orders.setRestaurantGroupId(1L);

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(1L);
            orderItem.setPrice(BigDecimal.TEN);

            orders.setOrderItems(Lists.newArrayList(orderItem));

            RestaurantMenuItemDto restaurantMenuItemDto = new RestaurantMenuItemDto();
            restaurantMenuItemDto.setMenuItemId(1L);
            restaurantMenuItemDto.setPrice(BigDecimal.ONE);
            restaurantMenuItemDto.setAvailable(true);

            when(restaurantClientAdapter.getRestaurantMenuItem(1L, 1L))
                    .thenReturn(Lists.newArrayList(restaurantMenuItemDto));

            validateOrderStepHandler.process(orders);
        });
    }

    @Test
    void shouldThrowsExceptionWhenMenuItemIsUnknown() {
        assertThrows(UnknownMenuItemException.class, () -> {
            Orders orders = new Orders();
            orders.setRestaurantId(1L);
            orders.setRestaurantGroupId(1L);

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(1L);
            orderItem.setPrice(BigDecimal.TEN);

            orders.setOrderItems(Lists.newArrayList(orderItem));

            RestaurantMenuItemDto restaurantMenuItemDto = new RestaurantMenuItemDto();
            restaurantMenuItemDto.setMenuItemId(1L);
            restaurantMenuItemDto.setPrice(BigDecimal.TEN);
            restaurantMenuItemDto.setAvailable(false);

            when(restaurantClientAdapter.getRestaurantMenuItem(1L, 1L))
                    .thenReturn(Lists.newArrayList(restaurantMenuItemDto));

            validateOrderStepHandler.process(orders);
        });
    }
}
