package com.lattaf.perfumes.order.dto;

import java.math.BigDecimal;

public record OrderItem(String skuCode, BigDecimal price, Integer quantity) {
}
