package com.lattaf.perfumes.order.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;

public record OrderRequest(
        //Long id,
        //String orderNumber,
        List<OrderItem> items,
        UserDetails userDetails
) {
    public record UserDetails(String email, String firstName, String lastName) {}
}
