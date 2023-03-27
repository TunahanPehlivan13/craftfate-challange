package com.craftgate.order.controller;

import com.craftgate.order.converter.OrderConverter;
import com.craftgate.order.dto.OrderDto;
import com.craftgate.order.dto.StatusDto;
import com.craftgate.order.entitiy.Orders;
import com.craftgate.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping
    public ResponseEntity placeOrder(@RequestBody @Valid OrderDto order) {
        Orders orders = orderConverter.to(order);
        StatusDto statusDto = orderService.placeOrder(orders);
        return ResponseEntity.ok(statusDto);
    }
}
