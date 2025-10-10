package com.lattaf.perfumes.product.service;

import com.lattaf.perfumes.product.dto.ProductRequest;
import com.lattaf.perfumes.product.dto.ProductResponse;
import com.lattaf.perfumes.product.model.Product;
import com.lattaf.perfumes.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .skuCode(productRequest.skuCode())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created successfully: {}", product);
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice()
        );
    }

    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> products = productRepository.findAll()
                        .stream()
                        .map(product -> new ProductResponse(
                                product.getId(),
                                product.getName(),
                                product.getDescription(),
                                product.getPrice()))
                        .toList();
        log.info("Successfully retrieved all products");
        return products;
    }

}
