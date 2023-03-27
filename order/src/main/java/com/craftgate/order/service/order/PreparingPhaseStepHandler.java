package com.craftgate.order.service.order;

import com.craftgate.order.entitiy.OrderItem;
import com.craftgate.order.entitiy.Orders;
import com.craftgate.order.enums.OrderStatus;
import com.craftgate.order.repository.OrderRepository;
import com.craftgate.order.service.order.constant.StepHandlerOrderConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Order(StepHandlerOrderConstant.PREPARING)
public class PreparingPhaseStepHandler implements OrderStepHandler {

    private final OrderRepository orderRepository;

    @Override
    public void process(Orders orders) {
        BigDecimal totalPrice = getTotalPrice(orders.getOrderItems());
        orders.setTotalPrice(totalPrice);

        orders.setOrderStatus(OrderStatus.PREPARING);
        orderRepository.save(orders);
    }

    @Override
    public void rollback(Orders orders) {
    }

    private BigDecimal getTotalPrice(List<OrderItem> orderItems) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalPrice = totalPrice.add(orderItem.getPrice());
        }
        return totalPrice;
    }
}
