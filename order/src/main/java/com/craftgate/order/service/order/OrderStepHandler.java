package com.craftgate.order.service.order;

import com.craftgate.order.entitiy.Orders;

public interface OrderStepHandler {

    void process(Orders orders);

    void rollback(Orders orders);
}
