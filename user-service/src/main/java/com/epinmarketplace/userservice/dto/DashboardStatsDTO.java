package com.epinmarketplace.userservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DashboardStatsDTO {
    private long totalUsers;
    private long totalProducts;
    private long totalOrders;
    private BigDecimal totalRevenue;
    private double revenueChange;
    private double ordersChange;
    private double usersChange;
    private double productsChange;
    private long activeUsers;
    private long pendingOrders;
    private long completedOrders;
    private long lowStockProducts;
    private LocalDateTime lastUpdated;

    // Constructors
    public DashboardStatsDTO() {}

    public DashboardStatsDTO(long totalUsers, long totalProducts, long totalOrders, BigDecimal totalRevenue,
                           double revenueChange, double ordersChange, double usersChange, double productsChange,
                           long activeUsers, long pendingOrders, long completedOrders, long lowStockProducts) {
        this.totalUsers = totalUsers;
        this.totalProducts = totalProducts;
        this.totalOrders = totalOrders;
        this.totalRevenue = totalRevenue;
        this.revenueChange = revenueChange;
        this.ordersChange = ordersChange;
        this.usersChange = usersChange;
        this.productsChange = productsChange;
        this.activeUsers = activeUsers;
        this.pendingOrders = pendingOrders;
        this.completedOrders = completedOrders;
        this.lowStockProducts = lowStockProducts;
        this.lastUpdated = LocalDateTime.now();
    }

    // Getters and Setters
    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(long totalOrders) {
        this.totalOrders = totalOrders;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getRevenueChange() {
        return revenueChange;
    }

    public void setRevenueChange(double revenueChange) {
        this.revenueChange = revenueChange;
    }

    public double getOrdersChange() {
        return ordersChange;
    }

    public void setOrdersChange(double ordersChange) {
        this.ordersChange = ordersChange;
    }

    public double getUsersChange() {
        return usersChange;
    }

    public void setUsersChange(double usersChange) {
        this.usersChange = usersChange;
    }

    public double getProductsChange() {
        return productsChange;
    }

    public void setProductsChange(double productsChange) {
        this.productsChange = productsChange;
    }

    public long getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(long activeUsers) {
        this.activeUsers = activeUsers;
    }

    public long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public long getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(long completedOrders) {
        this.completedOrders = completedOrders;
    }

    public long getLowStockProducts() {
        return lowStockProducts;
    }

    public void setLowStockProducts(long lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
