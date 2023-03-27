package com.craftgate.order.converter;

import com.craftgate.order.dto.OrderItemDto;
import com.craftgate.order.entitiy.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {

    public OrderItem to(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItemId(orderItemDto.getMenuItemId());
        orderItem.setPrice(orderItemDto.getPrice());
        return orderItem;
    }
}
