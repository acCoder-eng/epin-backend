package com.epinmarketplace.coupon_service.dto;

import com.epinmarketplace.coupon_service.entity.Coupon;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CouponRequest {
    
    private String code;
    private String name;
    private String description;
    private Coupon.DiscountType discountType;
    private Double discountValue;
    private Double minOrderAmount;
    private Double maxDiscountAmount;
    private Integer usageLimit;
    private Boolean isActive;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime validFrom;
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime validUntil;
    
    // Constructors
    public CouponRequest() {}
    
    // Getters and Setters
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
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
    
    public Coupon.DiscountType getDiscountType() {
        return discountType;
    }
    
    public void setDiscountType(Coupon.DiscountType discountType) {
        this.discountType = discountType;
    }
    
    public Double getDiscountValue() {
        return discountValue;
    }
    
    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }
    
    public Double getMinOrderAmount() {
        return minOrderAmount;
    }
    
    public void setMinOrderAmount(Double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }
    
    public Double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }
    
    public void setMaxDiscountAmount(Double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }
    
    public Integer getUsageLimit() {
        return usageLimit;
    }
    
    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public LocalDateTime getValidFrom() {
        return validFrom;
    }
    
    public void setValidFrom(LocalDateTime validFrom) {
        this.validFrom = validFrom;
    }
    
    public LocalDateTime getValidUntil() {
        return validUntil;
    }
    
    public void setValidUntil(LocalDateTime validUntil) {
        this.validUntil = validUntil;
    }
    
    // Convert to Coupon entity
    public Coupon toCoupon() {
        Coupon coupon = new Coupon();
        coupon.setCode(this.code);
        coupon.setName(this.name);
        coupon.setDescription(this.description);
        coupon.setDiscountType(this.discountType);
        coupon.setDiscountValue(this.discountValue);
        coupon.setMinOrderAmount(this.minOrderAmount);
        coupon.setMaxDiscountAmount(this.maxDiscountAmount);
        coupon.setUsageLimit(this.usageLimit);
        coupon.setIsActive(this.isActive);
        coupon.setValidFrom(this.validFrom);
        coupon.setValidUntil(this.validUntil);
        return coupon;
    }
}
