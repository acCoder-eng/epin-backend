package com.epinmarketplace.order_service.dto;

import java.time.LocalDateTime;

public class WishlistItemDto {
    
    private Long id;
    
    private Long userId;
    
    private Long productId;
    
    private LocalDateTime createdAt;
    
    // Product details (populated from Product Service)
    private String productName;
    private String productImage;
    private java.math.BigDecimal productPrice;
    private String productCurrency;
    private String productDescription;
    
    // Constructors
    public WishlistItemDto() {}
    
    public WishlistItemDto(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
    
    public String getProductDescription() {
        return productDescription;
    }
    
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
}
