package com.lattaf.perfumes.product.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(
       String id,
       String name,
       String description,
       String status,
       List<String> images,
       String category,
       Boolean isFeatured,
       BigDecimal price
) {
}
