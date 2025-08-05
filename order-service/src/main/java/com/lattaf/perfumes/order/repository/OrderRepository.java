package com.lattaf.perfumes.order.repository;

import com.lattaf.perfumes.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
