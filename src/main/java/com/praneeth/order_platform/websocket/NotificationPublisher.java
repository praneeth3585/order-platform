package com.praneeth.order_platform.websocket;

import com.praneeth.order_platform.entity.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class NotificationPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public NotificationPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(Notification notification) {

        messagingTemplate.convertAndSend(
                "/topic/notifications",
                notification
        );
    }
}