package com.praneeth.order_platform.dto;

import lombok.Data;

@Data
public class OrderRequest {

    private Long customerId;
    private String productName;
    private Double amount;
}