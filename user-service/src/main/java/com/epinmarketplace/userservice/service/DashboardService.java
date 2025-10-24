package com.epinmarketplace.userservice.service;

import com.epinmarketplace.userservice.dto.DashboardStatsDTO;
import com.epinmarketplace.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    public DashboardStatsDTO getDashboardStats() {
        // Get current stats
        long totalUsers = userRepository.count();
        long activeUsers = userRepository.countByIsActiveTrue();
        
        // For now, we'll use mock data for other services
        // In a real microservices architecture, you would call other services
        long totalProducts = 15; // Mock data - would come from product service
        long totalOrders = 1; // Mock data - would come from order service
        BigDecimal totalRevenue = new BigDecimal("25.00"); // Mock data - would come from order service
        long pendingOrders = 0; // Mock data
        long completedOrders = 1; // Mock data
        long lowStockProducts = 2; // Mock data - products with stock < 10
        
        // Calculate changes (mock data for now)
        // In a real implementation, you would compare with previous period data
        double usersChange = 15.3; // Mock: 15.3% increase
        double productsChange = -2.1; // Mock: 2.1% decrease
        double ordersChange = 8.2; // Mock: 8.2% increase
        double revenueChange = 12.5; // Mock: 12.5% increase
        
        return new DashboardStatsDTO(
            totalUsers, totalProducts, totalOrders, totalRevenue,
            revenueChange, ordersChange, usersChange, productsChange,
            activeUsers, pendingOrders, completedOrders, lowStockProducts
        );
    }
}
