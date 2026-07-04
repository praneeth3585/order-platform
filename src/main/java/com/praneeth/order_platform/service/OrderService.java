package com.praneeth.order_platform.service;

import com.praneeth.order_platform.entity.Order;
import com.praneeth.order_platform.enums.OrderStatus;
import com.praneeth.order_platform.kafka.OrderProducer;
import com.praneeth.order_platform.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProducer orderProducer;

    public OrderService(
            OrderRepository orderRepository,
            OrderProducer orderProducer) {

        this.orderRepository = orderRepository;
        this.orderProducer = orderProducer;
    }

    public Order createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        orderProducer.publishOrder(savedOrder);

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}