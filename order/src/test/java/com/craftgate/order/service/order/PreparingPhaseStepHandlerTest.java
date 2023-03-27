package com.craftgate.order.service.order;

import com.craftgate.order.dto.OrderDto;
import com.craftgate.order.entitiy.OrderItem;
import com.craftgate.order.entitiy.Orders;
import com.craftgate.order.enums.OrderStatus;
import com.craftgate.order.repository.OrderRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PreparingPhaseStepHandlerTest {

    @InjectMocks
    PreparingPhaseStepHandler preparingPhaseStepHandler;

    @Mock
    private OrderRepository orderRepository;

    @Test
    void shouldProcessPreparingPhase() {
        Orders orders = new Orders();

        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrders(orders);
        orderItem1.setPrice(BigDecimal.TEN);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrders(orders);
        orderItem2.setPrice(BigDecimal.ONE);

        orders.setOrderItems(Lists.newArrayList(orderItem1, orderItem2));

        preparingPhaseStepHandler.process(orders);

        ArgumentCaptor<Orders> captor = ArgumentCaptor.forClass(Orders.class);
        verify(orderRepository).save(captor.capture());
        Orders capturedOrder = captor.getValue();

        assertTrue(capturedOrder.getTotalPrice().compareTo(BigDecimal.valueOf(11d)) == 0);
        assertThat(capturedOrder.getOrderStatus(), is(OrderStatus.PREPARING));
    }
}
