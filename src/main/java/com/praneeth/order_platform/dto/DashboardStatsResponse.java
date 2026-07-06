package com.praneeth.order_platform.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsResponse {

    private Long totalOrders;

    private Double totalRevenue;

    private Long totalNotifications;

    private Double averageOrderValue;
}