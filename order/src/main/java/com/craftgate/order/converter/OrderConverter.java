package com.craftgate.order.converter;

import com.craftgate.order.dto.OrderDto;
import com.craftgate.order.dto.OrderItemDto;
import com.craftgate.order.entitiy.OrderItem;
import com.craftgate.order.entitiy.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final OrderItemConverter orderItemConverter;

    public Orders to(OrderDto orderDto) {
        Orders orders = new Orders();
        orders.setRestaurantId(orderDto.getRestaurantId());
        orders.setRestaurantGroupId(orderDto.getRestaurantGroupId());

        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDto orderItemDto : orderDto.getOrderItems()) {
            OrderItem orderItem = orderItemConverter.to(orderItemDto);
            orderItem.setOrders(orders);
            orderItems.add(orderItem);
        }
        orders.setOrderItems(orderItems);

        return orders;
    }
}
