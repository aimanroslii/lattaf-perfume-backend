package com.lattaf.perfumes.product.repository;

import com.lattaf.perfumes.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface ProductRepository extends MongoRepository<Product, String> {
    // Additional query methods can be defined here if needed
}
