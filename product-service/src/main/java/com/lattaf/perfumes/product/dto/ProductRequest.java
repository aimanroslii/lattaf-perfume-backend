package com.lattaf.perfumes.product.dto;

import lombok.Builder;

import java.math.BigDecimal;

public record ProductRequest(
       String name,
       String description,
       BigDecimal price
) {
}
