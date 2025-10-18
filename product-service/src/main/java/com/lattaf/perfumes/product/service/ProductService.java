package com.lattaf.perfumes.product.service;

import com.lattaf.perfumes.product.dto.ProductRequest;
import com.lattaf.perfumes.product.dto.ProductResponse;
import com.lattaf.perfumes.product.model.Product;
import com.lattaf.perfumes.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .status(productRequest.status())
                .images(productRequest.images())
                .category(productRequest.category())
                .isFeatured(productRequest.isFeatured())
                .price(productRequest.price())
                .build();
        productRepository.save(product);
        log.info("Product created successfully: {}", product);
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImages(),
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
                                product.getImages(),
                                product.getPrice()))
                        .toList();
        log.info("Successfully retrieved all products");
        return products;
    }

    public List<ProductResponse> getFeaturedProducts() {
        List<ProductResponse> featuredProducts = productRepository.findByIsFeaturedTrue()
                .stream()
                .limit(3)
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImages(),
                        product.getPrice()))
                .toList();
        log.info("Successfully retrieved featured products (limit 3)");
        return featuredProducts;
    }

    public List<ProductResponse> getProductsByCategory(String category) {
        List<ProductResponse> productsByCategory = productRepository.findByCategoryIgnoreCase(category)
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getImages(),
                        product.getPrice()))
                .toList();

        log.info("Retrieved all products for category: {}", category);
        return productsByCategory;
    }

    public ProductResponse getProductById(String id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        log.info("Successfully retrieved product with id: {}", id);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImages(),
                product.getPrice()
        );
    }

    @Transactional
    public Product updateProduct(String id, ProductRequest productRequest){

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        if(productRequest.name() != null) product.setName(productRequest.name());
        if(productRequest.description() != null) product.setDescription(productRequest.description());
        if(productRequest.status() != null) product.setStatus(productRequest.status());
        if(productRequest.images() != null) product.setImages(productRequest.images());
        if(productRequest.category() != null) product.setCategory(productRequest.category());
        if(productRequest.isFeatured() != null) product.setIsFeatured(productRequest.isFeatured());
        if(productRequest.price() != null) product.setPrice(productRequest.price());

        Product updatedProduct = productRepository.save(product);
        log.info("Product updated successfully: id={}", id);
        return updatedProduct;
    }

    @Transactional
    public void deleteProduct(String id) {
        boolean exists = productRepository.existsById(id);
        if(!exists) {
            throw new RuntimeException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
        log.info("Product deleted successfully, id={}", id);
    }

}

