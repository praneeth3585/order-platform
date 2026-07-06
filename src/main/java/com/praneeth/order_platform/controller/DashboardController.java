package com.praneeth.order_platform.controller;

import com.praneeth.order_platform.dto.DashboardStatsResponse;
import com.praneeth.order_platform.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public DashboardStatsResponse getDashboardStats() {
        return dashboardService.getDashboardStats();
    }
}