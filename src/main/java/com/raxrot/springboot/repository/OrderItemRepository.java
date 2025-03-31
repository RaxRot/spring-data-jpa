package com.raxrot.springboot.repository;

import com.raxrot.springboot.entity.MyTests.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
