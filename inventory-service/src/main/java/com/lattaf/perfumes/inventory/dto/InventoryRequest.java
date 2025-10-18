package com.lattaf.perfumes.inventory.dto;

public record InventoryRequest(Long id, String skuCode, Integer quantity) {
}
