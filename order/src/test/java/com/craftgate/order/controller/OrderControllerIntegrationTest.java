package com.craftgate.order.controller;

import com.craftgate.order.converter.OrderConverter;
import com.craftgate.order.dto.OrderDto;
import com.craftgate.order.dto.OrderItemDto;
import com.craftgate.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    @MockBean
    OrderConverter orderConverter;

    @Test
    public void shouldReturnOkWhenPlacingOrder() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setRestaurantGroupId(1L);
        orderDto.setRestaurantId(1L);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setMenuItemId(1L);
        orderItemDto.setPrice(BigDecimal.TEN);

        orderDto.setOrderItems(Lists.newArrayList(orderItemDto));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(orderDto)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void shouldReturnBadRequestWhenPlacingOrderIfRestaurantGroupIdIsNull() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setRestaurantId(1L);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setMenuItemId(1L);
        orderItemDto.setPrice(BigDecimal.TEN);

        orderDto.setOrderItems(Lists.newArrayList(orderItemDto));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(orderDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturnBadRequestWhenPlacingOrderIfRestaurantIdIsNull() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setRestaurantGroupId(1L);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setMenuItemId(1L);
        orderItemDto.setPrice(BigDecimal.TEN);

        orderDto.setOrderItems(Lists.newArrayList(orderItemDto));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(orderDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturnBadRequestWhenPlacingOrderIfMenuItemIdIsNull() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setRestaurantGroupId(1L);
        orderDto.setRestaurantId(1L);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setPrice(BigDecimal.TEN);

        orderDto.setOrderItems(Lists.newArrayList(orderItemDto));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(orderDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }

    @Test
    public void shouldReturnBadRequestWhenPlacingOrderIfPriceIsNull() throws Exception {
        OrderDto orderDto = new OrderDto();
        orderDto.setRestaurantGroupId(1L);
        orderDto.setRestaurantId(1L);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setMenuItemId(1L);

        orderDto.setOrderItems(Lists.newArrayList(orderItemDto));

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(orderDto)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
