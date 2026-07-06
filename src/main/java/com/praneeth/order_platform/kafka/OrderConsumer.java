package com.praneeth.order_platform.kafka;

import com.praneeth.order_platform.entity.Order;
import com.praneeth.order_platform.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(OrderConsumer.class);

    private final NotificationService notificationService;

    public OrderConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "orders-topic",
            groupId = "order-group"
    )
    public void consume(Order order) {

        logger.info("=================================");
        logger.info("Order Event Received");
        logger.info("Order ID      : {}", order.getId());
        logger.info("Customer ID   : {}", order.getCustomerId());
        logger.info("Product Name  : {}", order.getProductName());
        logger.info("Amount        : {}", order.getAmount());
        logger.info("Status        : {}", order.getStatus());
        logger.info("=================================");

        notificationService.createNotification(
                order.getCustomerId(),
                order.getProductName()
        );

        logger.info("Notification created successfully for Customer ID: {}",
                order.getCustomerId());
    }
}