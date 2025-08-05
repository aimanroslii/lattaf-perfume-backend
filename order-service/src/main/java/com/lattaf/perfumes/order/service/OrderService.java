package com.lattaf.perfumes.order.service;

import com.lattaf.perfumes.order.client.InventoryClient;
import com.lattaf.perfumes.order.dto.OrderRequest;
import com.lattaf.perfumes.order.model.Order;
import com.lattaf.perfumes.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){
        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        // Map OrderRequest to Order object
        if(isProductInStock){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setSkuCode(orderRequest.skuCode());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());

            // Save order to OrderRepository
            orderRepository.save(order);
            log.info("Order placed successfully with order number: {}", order.getOrderNumber());
        } else {
            throw new RuntimeException("Product with SkuCode "+ orderRequest.skuCode()+ " is not in stock");
        }
    }
}
