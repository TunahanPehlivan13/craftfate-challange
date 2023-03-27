package com.craftgate.order.service;

import com.craftgate.order.dto.StatusDto;
import com.craftgate.order.entitiy.Orders;
import com.craftgate.order.exception.PaymentValidationException;
import com.craftgate.order.service.order.OrderStepHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final List<OrderStepHandler> orderStepHandlers;

    public StatusDto placeOrder(Orders orders) {

        for (OrderStepHandler orderStepHandler : orderStepHandlers) {
            try {
                log.info("{} will be started for order-id={}", orders.getClass().getName(), orders.getId());
                orderStepHandler.process(orders);
            } catch (PaymentValidationException pve) {
                return pve.getStatusDto();
            } catch (Exception e) {
                log.error("Failed to process {} for order-id={}", orders.getId(), e);
                startFailoverScenarios(orders, orderStepHandler);
                return new StatusDto(true, "unknown error");
            }
        }
        return generateSuccessfulMessage("Order has been successfully created for order-id=" + orders.getId());
    }

    private void startFailoverScenarios(Orders orders, OrderStepHandler orderStepHandler) {
        // TODO implement failover scenario
    }

    private StatusDto generateSuccessfulMessage(String message) {
        StatusDto statusDto = new StatusDto();
        statusDto.setError(false);
        statusDto.setMessage(message);
        return statusDto;
    }
}
