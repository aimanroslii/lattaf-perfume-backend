package com.lattaf.perfumes.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

public record ProductRequest(
       String id,
       String name,
       String description,
       String skuCode,
       BigDecimal price
) {
}
