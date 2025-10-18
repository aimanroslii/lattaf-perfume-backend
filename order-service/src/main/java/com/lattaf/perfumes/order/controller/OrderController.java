package com.lattaf.perfumes.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lattaf.perfumes.order.dto.OrderRequest;
import com.lattaf.perfumes.order.dto.OrderResponse;
import com.lattaf.perfumes.order.dto.ProductResponse;
import com.lattaf.perfumes.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse placeOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
        return orderService.placeOrder(orderRequest);
    }
}
