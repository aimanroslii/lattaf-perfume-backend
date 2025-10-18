package com.lattaf.perfumes.inventory.controller;

import com.lattaf.perfumes.inventory.dto.InventoryRequest;
import com.lattaf.perfumes.inventory.dto.InventoryResponse;
import com.lattaf.perfumes.inventory.model.Inventory;
import com.lattaf.perfumes.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        // Check if the product is in stock
        return inventoryService.isInStock(skuCode, quantity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse storeItem(@RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.storeItem(inventoryRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Inventory updateItem(@PathVariable Long id, @RequestBody InventoryRequest inventoryRequest){
        return inventoryService.updateItem(id, inventoryRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long id) {
        inventoryService.deleteItem(id);
    }
}

