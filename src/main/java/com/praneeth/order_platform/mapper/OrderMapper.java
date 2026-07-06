package com.praneeth.order_platform.mapper;

import com.praneeth.order_platform.dto.OrderRequest;
import com.praneeth.order_platform.dto.OrderResponse;
import com.praneeth.order_platform.entity.Order;

public class OrderMapper {

    public static Order toEntity(OrderRequest request) {

        Order order = new Order();

        order.setCustomerId(request.getCustomerId());
        order.setProductName(request.getProductName());
        order.setAmount(request.getAmount());

        return order;
    }

    public static OrderResponse toResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .productName(order.getProductName())
                .amount(order.getAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}