package com.praneeth.order_platform.repository;

import com.praneeth.order_platform.entity.Order;
import com.praneeth.order_platform.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT COALESCE(SUM(o.amount), 0) FROM Order o")
    Double getTotalRevenue();

    @Query("SELECT COALESCE(AVG(o.amount), 0) FROM Order o")
    Double getAverageOrderValue();

    List<Order> findByProductNameContainingIgnoreCase(String productName);

    List<Order> findByCustomerId(Long customerId);

    List<Order> findByStatus(OrderStatus status);
}