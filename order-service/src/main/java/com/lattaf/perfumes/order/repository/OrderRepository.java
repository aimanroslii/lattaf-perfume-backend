package com.lattaf.perfumes.order.repository;

import com.lattaf.perfumes.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findById(Long id);
    Optional<Order> findByOrderNumber(String orderNumber);
    void deleteById(Long id);
    boolean existsById(Long id);
}
