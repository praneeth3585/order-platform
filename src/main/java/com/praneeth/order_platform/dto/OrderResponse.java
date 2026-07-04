package com.praneeth.order_platform.dto;

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
    private String status;
    private LocalDateTime createdAt;
}