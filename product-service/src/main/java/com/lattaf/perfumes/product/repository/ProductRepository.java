package com.lattaf.perfumes.product.repository;

import com.lattaf.perfumes.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByIsFeaturedTrue();
    List<Product> findByCategoryIgnoreCase(String category);
    Optional<Product> findById(String id);
    void deleteById(String id);
    boolean existsById(String s);
}
