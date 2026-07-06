package com.praneeth.order_platform.service;

import com.praneeth.order_platform.dto.DashboardStatsResponse;
import com.praneeth.order_platform.repository.NotificationRepository;
import com.praneeth.order_platform.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final OrderRepository orderRepository;
    private final NotificationRepository notificationRepository;

    public DashboardService(OrderRepository orderRepository,
                            NotificationRepository notificationRepository) {
        this.orderRepository = orderRepository;
        this.notificationRepository = notificationRepository;
    }

    public DashboardStatsResponse getDashboardStats() {

        long totalOrders = orderRepository.count();

        double totalRevenue = orderRepository.getTotalRevenue();

        long totalNotifications = notificationRepository.count();

        double averageOrderValue = orderRepository.getAverageOrderValue();

        return DashboardStatsResponse.builder()
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .totalNotifications(totalNotifications)
                .averageOrderValue(averageOrderValue)
                .build();
    }
}