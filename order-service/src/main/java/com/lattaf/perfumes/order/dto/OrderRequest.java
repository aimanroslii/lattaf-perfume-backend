package com.lattaf.perfumes.order.dto;

import java.math.BigDecimal;
import java.util.function.BiConsumer;

public record OrderRequest(
        Long id,
        String orderNumber,
        String skuCode,
        BigDecimal price,
        Integer quantity
) {
}
