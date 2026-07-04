package com.praneeth.order_platform.service;

import com.praneeth.order_platform.entity.Notification;
import com.praneeth.order_platform.repository.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(NotificationRepository notificationRepository,
                               SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public Notification createNotification(Long customerId, String productName) {

        Notification notification = Notification.builder()
                .customerId(customerId)
                .message("Your order for " + productName + " has been created successfully.")
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        Notification savedNotification = notificationRepository.save(notification);

        // Send real-time notification through WebSocket
        messagingTemplate.convertAndSend(
                "/topic/notifications",
                savedNotification
        );

        return savedNotification;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}