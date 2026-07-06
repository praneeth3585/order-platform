package com.praneeth.order_platform.dto;

import com.praneeth.order_platform.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {

    private Long id;
    private Long customerId;
    private String productName;
    private Double amount;
    private OrderStatus status;
    private LocalDateTime createdAt;
}