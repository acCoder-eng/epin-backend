package com.epinmarketplace.product_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class ProductDto {
    
    private Long id;
    
    @NotBlank(message = "Product name is required")
    @Size(max = 200, message = "Product name must not exceed 200 characters")
    private String name;
    
    private String description;
    
    private Long categoryId;
    
    private Long sellerId;
    
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;
    
    private BigDecimal originalPrice;
    
    @Size(max = 3, message = "Currency must not exceed 3 characters")
    private String currency = "USD";
    
    @Min(value = 0, message = "Stock quantity must be non-negative")
    private Integer stockQuantity = 0;
    
    @Min(value = 1, message = "Minimum quantity must be at least 1")
    private Integer minQuantity = 1;
    
    private Integer maxQuantity;
    
    private String imageUrl;
    
    @Size(max = 50, message = "Pin format must not exceed 50 characters")
    private String pinFormat;
    
    @Size(max = 50, message = "Pin type must not exceed 50 characters")
    private String pinType;
    
    @Size(max = 50, message = "Region must not exceed 50 characters")
    private String region;
    
    @Size(max = 100, message = "Platform must not exceed 100 characters")
    private String platform;
    
    private Boolean isActive = true;
    
    private Boolean isFeatured = false;
    
    private BigDecimal rating = BigDecimal.ZERO;
    
    private Integer reviewCount = 0;
    
    private Integer salesCount = 0;
    
    // Constructors
    public ProductDto() {}
    
    public ProductDto(String name, String description, Long categoryId, Long sellerId, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.sellerId = sellerId;
        this.price = price;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Long getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    
    public Long getSellerId() {
        return sellerId;
    }
    
    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }
    
    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    
    public Integer getMinQuantity() {
        return minQuantity;
    }
    
    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }
    
    public Integer getMaxQuantity() {
        return maxQuantity;
    }
    
    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getPinFormat() {
        return pinFormat;
    }
    
    public void setPinFormat(String pinFormat) {
        this.pinFormat = pinFormat;
    }
    
    public String getPinType() {
        return pinType;
    }
    
    public void setPinType(String pinType) {
        this.pinType = pinType;
    }
    
    public String getRegion() {
        return region;
    }
    
    public void setRegion(String region) {
        this.region = region;
    }
    
    public String getPlatform() {
        return platform;
    }
    
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public Boolean getIsFeatured() {
        return isFeatured;
    }
    
    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }
    
    public BigDecimal getRating() {
        return rating;
    }
    
    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
    
    public Integer getReviewCount() {
        return reviewCount;
    }
    
    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }
    
    public Integer getSalesCount() {
        return salesCount;
    }
    
    public void setSalesCount(Integer salesCount) {
        this.salesCount = salesCount;
    }
}
