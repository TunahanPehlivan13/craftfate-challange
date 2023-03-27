package com.craftgate.order.service.order;

import com.craftgate.order.client.RestaurantClientAdapter;
import com.craftgate.order.client.dto.RestaurantMenuItemDto;
import com.craftgate.order.dto.StatusDto;
import com.craftgate.order.entitiy.OrderItem;
import com.craftgate.order.entitiy.Orders;
import com.craftgate.order.exception.MismatchedPriceException;
import com.craftgate.order.exception.NoValidMenuItemException;
import com.craftgate.order.exception.UnknownMenuItemException;
import com.craftgate.order.service.order.constant.StepHandlerOrderConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Order(StepHandlerOrderConstant.VALIDATION)
public class ValidateOrderStepHandler implements OrderStepHandler {

    private final RestaurantClientAdapter restaurantClientAdapter;

    @Override
    public void process(Orders orders) {
        Iterable<RestaurantMenuItemDto> restaurantMenuItems = restaurantClientAdapter
                .getRestaurantMenuItem(orders.getRestaurantGroupId(), orders.getRestaurantId());

        validateOrderItems(orders, restaurantMenuItems);
    }

    @Override
    public void rollback(Orders orders) {
    }

    private void validateOrderItems(Orders orders, Iterable<RestaurantMenuItemDto> restaurantMenuItems) {
        for (OrderItem orderItem : orders.getOrderItems()) {
            checkAvailabilityAndPrice(orderItem, restaurantMenuItems);
        }
    }

    private void checkAvailabilityAndPrice(OrderItem orderItem, Iterable<RestaurantMenuItemDto> restaurantMenuItems) {
        Optional<RestaurantMenuItemDto> mayRestaurantMenuItem = findMenuItem(orderItem.getMenuItemId(), restaurantMenuItems);
        if (mayRestaurantMenuItem.isEmpty()) {
            StatusDto statusDto = generateErrorMessage("No valid menu-item for menu-item-id=" + orderItem.getMenuItemId());
            throw new NoValidMenuItemException(statusDto);
        }
        RestaurantMenuItemDto restaurantMenuItemDto = mayRestaurantMenuItem.get();
        if (!restaurantMenuItemDto.getAvailable()) {
            StatusDto statusDto = generateErrorMessage("Unknown menu-item for menu-item-id=" + orderItem.getMenuItemId());
            throw new UnknownMenuItemException(statusDto);
        }
        if (restaurantMenuItemDto.getPrice().compareTo(orderItem.getPrice()) != 0) {
            StatusDto statusDto = generateErrorMessage("Mismatched price for menu-item-id=" + orderItem.getMenuItemId()
                    + ", posted-price=" + orderItem.getPrice() + ", valid-price=" + restaurantMenuItemDto.getPrice());
            throw new MismatchedPriceException(statusDto);
        }
    }

    private Optional<RestaurantMenuItemDto> findMenuItem(Long menuItemId, Iterable<RestaurantMenuItemDto> restaurantMenuItems) {
        Iterator<RestaurantMenuItemDto> restaurantMenuItemDtoIterator = restaurantMenuItems.iterator();
        while (restaurantMenuItemDtoIterator.hasNext()) {
            RestaurantMenuItemDto restaurantMenuItem = restaurantMenuItemDtoIterator.next();
            if (restaurantMenuItem.getMenuItemId().equals(menuItemId)) {
                return Optional.of(restaurantMenuItem);
            }
        }
        return Optional.empty();
    }

    private StatusDto generateErrorMessage(String message) {
        StatusDto statusDto = new StatusDto();
        statusDto.setError(true);
        statusDto.setMessage(message);
        return statusDto;
    }
}
