package com.praneeth.order_platform.repository;

import com.praneeth.order_platform.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}