package com.praneeth.order_platform.kafka;

import com.praneeth.order_platform.entity.Order;
import com.praneeth.order_platform.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private final NotificationService notificationService;

    public OrderConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "orders-topic",
            groupId = "order-group"
    )
    public void consume(Order order) {

        System.out.println("=================================");
        System.out.println("Order Received");
        System.out.println("Order ID : " + order.getId());
        System.out.println("Customer ID : " + order.getCustomerId());
        System.out.println("Product : " + order.getProductName());
        System.out.println("=================================");

        notificationService.createNotification(
                order.getCustomerId(),
                order.getProductName()
        );
    }
}