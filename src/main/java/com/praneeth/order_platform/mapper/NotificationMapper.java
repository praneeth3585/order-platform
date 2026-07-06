package com.praneeth.order_platform.mapper;

import com.praneeth.order_platform.dto.NotificationResponse;
import com.praneeth.order_platform.entity.Notification;

public class NotificationMapper {

    public static NotificationResponse toResponse(Notification notification) {

        return NotificationResponse.builder()
                .id(notification.getId())
                .customerId(notification.getCustomerId())
                .message(notification.getMessage())
                .isRead(notification.getIsRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}