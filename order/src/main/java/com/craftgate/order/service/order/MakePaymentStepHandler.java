package com.craftgate.order.service.order;

import com.craftgate.order.entitiy.OrderItem;
import com.craftgate.order.entitiy.Orders;
import com.craftgate.order.enums.OrderStatus;
import com.craftgate.order.repository.OrderRepository;
import com.craftgate.order.service.order.constant.StepHandlerOrderConstant;
import io.craftgate.Craftgate;
import io.craftgate.model.Currency;
import io.craftgate.model.PaymentGroup;
import io.craftgate.model.PaymentPhase;
import io.craftgate.request.CreatePaymentRequest;
import io.craftgate.request.dto.Card;
import io.craftgate.request.dto.PaymentItem;
import io.craftgate.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Order(StepHandlerOrderConstant.MAKE_PAYMENT)
@RequiredArgsConstructor
@Slf4j
public class MakePaymentStepHandler implements OrderStepHandler {

    private final Craftgate craftgate;
    private final OrderRepository orderRepository;

    @Override
    public void process(Orders orders) {
        CreatePaymentRequest request = getCreatePaymentRequest(orders);

        PaymentResponse paymentResponse = craftgate.payment().createPayment(request);
        log.info("Payment successfully completed with payment-id=" + paymentResponse.getId());

        orders.setOrderStatus(OrderStatus.RECEIVED);
        orderRepository.save(orders);
    }

    @Override
    public void rollback(Orders orders) {
    }

    private CreatePaymentRequest getCreatePaymentRequest(Orders orders) {
        List<PaymentItem> items = new ArrayList<>();

        for (OrderItem orderItem : orders.getOrderItems()) {
            items.add(PaymentItem.builder()
                    .name(orderItem.getMenuItemId().toString())
                    .externalId(UUID.randomUUID().toString())
                    .price(orderItem.getPrice())
                    .build());
        }

        CreatePaymentRequest request = CreatePaymentRequest.builder()
                .price(orders.getTotalPrice())
                .paidPrice(orders.getTotalPrice())
                .walletPrice(BigDecimal.ZERO)
                .installment(1)
                .currency(Currency.TRY)
                .conversationId("456d1297-908e-4bd6-a13b-4be31a6e47d5")
                .paymentGroup(PaymentGroup.LISTING_OR_SUBSCRIPTION)
                .paymentPhase(PaymentPhase.AUTH)
                .card(Card.builder()
                        .cardHolderName("Haluk Demir")
                        .cardNumber("5258640000000001")
                        .expireYear("2044")
                        .expireMonth("07")
                        .cvc("000")
                        .build())
                .items(items)
                .build();
        return request;
    }
}
