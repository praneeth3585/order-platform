package com.praneeth.order_platform.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {

    private Long id;
    private Long customerId;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;
}