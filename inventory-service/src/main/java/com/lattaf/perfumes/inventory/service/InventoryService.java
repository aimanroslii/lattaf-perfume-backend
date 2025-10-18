package com.lattaf.perfumes.inventory.service;

import com.lattaf.perfumes.inventory.dto.InventoryRequest;
import com.lattaf.perfumes.inventory.dto.InventoryResponse;
import com.lattaf.perfumes.inventory.model.Inventory;
import com.lattaf.perfumes.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode, Integer quantity){
        // Find an inventory for a given skuCode where quantity >= 0
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

    @Transactional
    public InventoryResponse storeItem(InventoryRequest inventoryRequest){
        Inventory inventory = Inventory.builder()
                .skuCode(inventoryRequest.skuCode())
                .quantity(inventoryRequest.quantity())
                .build();
        inventoryRepository.save(inventory);
        log.info("Item stored successfully in inventory: {}", inventory);
        return new InventoryResponse(
                inventory.getId(),
                inventory.getSkuCode(),
                inventory.getQuantity()
        );
    }

    @Transactional
    public Inventory updateItem(Long id, InventoryRequest inventoryRequest) {

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found in inventory"));

        if(inventoryRequest.skuCode() != null) inventory.setSkuCode(inventoryRequest.skuCode());
        if(inventoryRequest.quantity() != null) inventory.setQuantity(inventoryRequest.quantity());

        Inventory updatedInventory = inventoryRepository.save(inventory);
        log.info("Inventory updated successfully: id={}", id);
        return updatedInventory;
    }

    @Transactional
    public void deleteItem(Long id) {
        boolean exists = inventoryRepository.existsById(id);
        if(!exists) {
            throw new RuntimeException("Item not exists in inventory");
        }
        inventoryRepository.deleteById(id);
        log.info("Item deleted successfully from the inventory, id={}",id);
    }

}
