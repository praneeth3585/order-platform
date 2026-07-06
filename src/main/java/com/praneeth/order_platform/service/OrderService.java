package com.praneeth.order_platform.service;

import com.praneeth.order_platform.entity.Order;
import com.praneeth.order_platform.enums.OrderStatus;
import com.praneeth.order_platform.exception.OrderNotFoundException;
import com.praneeth.order_platform.kafka.OrderProducer;
import com.praneeth.order_platform.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(OrderRepository orderRepository,
                        OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    // Create Order
    @CacheEvict(value = "orders", allEntries = true)
    public Order createOrder(Order order) {

        log.info("Creating new order for customer {}", order.getCustomerId());

        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        log.info("Order {} created successfully", savedOrder.getId());

        orderProducer.publishOrder(savedOrder);

        log.info("Order {} published to Kafka", savedOrder.getId());

        return savedOrder;
    }

    // Update Order Status
    @CacheEvict(value = "orders", allEntries = true)
    public Order updateOrderStatus(Long id, OrderStatus status) {

        log.info("Updating order {} to status {}", id, status);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order {} not found", id);
                    return new OrderNotFoundException("Order not found with ID: " + id);
                });

        order.setStatus(status);

        Order updatedOrder = orderRepository.save(order);

        log.info("Order {} updated successfully", id);

        orderProducer.publishOrder(updatedOrder);

        log.info("Updated order {} published to Kafka", id);

        return updatedOrder;
    }

    // Get All Orders
    @Cacheable("orders")
    public List<Order> getAllOrders() {

        log.info("Fetching all orders");

        return orderRepository.findAll();
    }

    // Pagination
    public Page<Order> getOrders(int page, int size) {

        log.info("Fetching orders page {} with size {}", page, size);

        Pageable pageable = PageRequest.of(page, size);

        return orderRepository.findAll(pageable);
    }

    // Search by Product
    public List<Order> searchByProduct(String productName) {

        log.info("Searching orders for product '{}'", productName);

        return orderRepository.findByProductNameContainingIgnoreCase(productName);
    }

    // Search by Customer
    public List<Order> searchByCustomer(Long customerId) {

        log.info("Searching orders for customer {}", customerId);

        return orderRepository.findByCustomerId(customerId);
    }

    // Filter by Status
    public List<Order> filterByStatus(OrderStatus status) {

        log.info("Filtering orders with status {}", status);

        return orderRepository.findByStatus(status);
    }
}