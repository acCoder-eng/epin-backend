package com.epinmarketplace.orderservice.dto;

import java.time.LocalDateTime;

public class WishlistItemDto {
    private Long id;
    private Long userId;
    private Long productId;
    private LocalDateTime createdAt;
    private ProductDto product;
    
    // Constructors
    public WishlistItemDto() {}
    
    public WishlistItemDto(Long id, Long userId, Long productId, LocalDateTime createdAt, ProductDto product) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.createdAt = createdAt;
        this.product = product;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public ProductDto getProduct() { return product; }
    public void setProduct(ProductDto product) { this.product = product; }
}
