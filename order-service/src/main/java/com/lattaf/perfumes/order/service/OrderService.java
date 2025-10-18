package com.lattaf.perfumes.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattaf.perfumes.order.client.InventoryClient;
import com.lattaf.perfumes.order.dto.OrderItem;
import com.lattaf.perfumes.order.dto.OrderRequest;
import com.lattaf.perfumes.order.dto.OrderResponse;
import com.lattaf.perfumes.order.event.OrderPlacedEvent;
import com.lattaf.perfumes.order.model.Order;
import com.lattaf.perfumes.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public final ObjectMapper objectMapper = new ObjectMapper();

    public OrderResponse placeOrder(OrderRequest orderRequest) throws JsonProcessingException {
        List<OrderItem> validateItems = new ArrayList<>();
        Integer totalQuantity = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;

        for(OrderItem item : orderRequest.items()) {
            boolean inStock = inventoryClient.isInStock(item.skuCode(), item.quantity());
            if(!inStock) {
                throw new RuntimeException("Product with SkuCode " + item.skuCode() + " is not in stock");
            }
            validateItems.add(item);
            totalQuantity += item.quantity();
            totalPrice = totalPrice.add(item.price().multiply(BigDecimal.valueOf(item.quantity())));
        }

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setSkuCode(objectMapper.writeValueAsString(validateItems));
        order.setPrice(totalPrice);
        order.setQuantity(totalQuantity);
        orderRepository.save(order);
        log.info("Order placed successfully with order number: {}", order.getOrderNumber());

        //Send the message to kafka topic
        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
        orderPlacedEvent.setOrderNumber(order.getOrderNumber());
        orderPlacedEvent.setEmail(orderRequest.userDetails().email());
        orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
        orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());
        log.info("Start - Sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);
        kafkaTemplate.send("order-placed", orderPlacedEvent);
        log.info("End - Sending OrderPlacedEvent {} to Kafka topic order-placed", orderPlacedEvent);

        return new OrderResponse("Order placed successfully");
    }

    @Transactional
    public Order getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        log.info("Order retrieved successfully for id:{}", order.getId());
        return order;
    }

    @Transactional
    public Order getOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order number not found"));

        log.info("Order number retrieved successfully for id:{} ", order.getOrderNumber());
        return order;
    }

    @Transactional
    public void deleteOrder(Long id){
        boolean exists = orderRepository.existsById(id);
        if(!exists) {
            throw new RuntimeException("Order not found");
        }

        orderRepository.deleteById(id);
        log.info("Order deleted successfully, id={}", id);
    }
}
