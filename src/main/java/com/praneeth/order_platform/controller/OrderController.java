package com.praneeth.order_platform.controller;

import com.praneeth.order_platform.dto.OrderRequest;
import com.praneeth.order_platform.dto.OrderResponse;
import com.praneeth.order_platform.entity.Order;
import com.praneeth.order_platform.enums.OrderStatus;
import com.praneeth.order_platform.mapper.OrderMapper;
import com.praneeth.order_platform.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create Order
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody OrderRequest request) {

        Order order = OrderMapper.toEntity(request);

        Order savedOrder = orderService.createOrder(order);

        return OrderMapper.toResponse(savedOrder);
    }

    // Get Orders with Pagination
    @GetMapping
    public Page<OrderResponse> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return orderService.getOrders(page, size)
                .map(OrderMapper::toResponse);
    }

    // Search by Product
    @GetMapping("/search/product")
    public List<OrderResponse> searchByProduct(
            @RequestParam String product) {

        return orderService.searchByProduct(product)
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    // Search by Customer
    @GetMapping("/search/customer")
    public List<OrderResponse> searchByCustomer(
            @RequestParam Long customerId) {

        return orderService.searchByCustomer(customerId)
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    // Filter by Status
    @GetMapping("/filter/status")
    public List<OrderResponse> filterByStatus(
            @RequestParam OrderStatus status) {

        return orderService.filterByStatus(status)
                .stream()
                .map(OrderMapper::toResponse)
                .toList();
    }

    // Update Order Status
    @PutMapping("/{id}/status")
    public OrderResponse updateStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {

        Order updatedOrder = orderService.updateOrderStatus(id, status);

        return OrderMapper.toResponse(updatedOrder);
    }
}