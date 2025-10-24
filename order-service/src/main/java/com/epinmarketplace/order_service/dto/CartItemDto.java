package com.epinmarketplace.order_service.dto;

import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

public class CartItemDto {
    
    private Long id;
    
    private Long userId;
    
    private Long productId;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    // Product details (populated from Product Service)
    private String productName;
    private String productImage;
    private java.math.BigDecimal productPrice;
    private String productCurrency;
    
    // Constructors
    public CartItemDto() {}
    
    public CartItemDto(Long userId, Long productId, Integer quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductImage() {
        return productImage;
    }
    
    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }
    
    public java.math.BigDecimal getProductPrice() {
        return productPrice;
    }
    
    public void setProductPrice(java.math.BigDecimal productPrice) {
        this.productPrice = productPrice;
    }
    
    public String getProductCurrency() {
        return productCurrency;
    }
    
    public void setProductCurrency(String productCurrency) {
        this.productCurrency = productCurrency;
    }
}
