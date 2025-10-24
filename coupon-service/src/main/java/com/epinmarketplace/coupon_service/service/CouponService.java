package com.epinmarketplace.coupon_service.service;

import com.epinmarketplace.coupon_service.entity.Coupon;
import com.epinmarketplace.coupon_service.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {
    
    @Autowired
    private CouponRepository couponRepository;
    
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }
    
    public List<Coupon> getActiveCoupons() {
        return couponRepository.findByIsActiveTrue();
    }
    
    public Optional<Coupon> getCouponById(Long id) {
        return couponRepository.findById(id);
    }
    
    public Optional<Coupon> getCouponByCode(String code) {
        return couponRepository.findByCode(code);
    }
    
    public Coupon createCoupon(Coupon coupon) {
        // Check if code already exists
        if (couponRepository.existsByCode(coupon.getCode())) {
            throw new RuntimeException("Coupon code already exists");
        }
        
        // Set default values
        if (coupon.getUsedCount() == null) {
            coupon.setUsedCount(0);
        }
        if (coupon.getIsActive() == null) {
            coupon.setIsActive(true);
        }
        
        return couponRepository.save(coupon);
    }
    
    public Coupon updateCoupon(Long id, Coupon couponDetails) {
        Coupon coupon = couponRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        
        // Check if code already exists (excluding current coupon)
        if (!coupon.getCode().equals(couponDetails.getCode()) && 
            couponRepository.existsByCode(couponDetails.getCode())) {
            throw new RuntimeException("Coupon code already exists");
        }
        
        coupon.setCode(couponDetails.getCode());
        coupon.setName(couponDetails.getName());
        coupon.setDescription(couponDetails.getDescription());
        coupon.setDiscountType(couponDetails.getDiscountType());
        coupon.setDiscountValue(couponDetails.getDiscountValue());
        coupon.setMinOrderAmount(couponDetails.getMinOrderAmount());
        coupon.setMaxDiscountAmount(couponDetails.getMaxDiscountAmount());
        coupon.setUsageLimit(couponDetails.getUsageLimit());
        coupon.setIsActive(couponDetails.getIsActive());
        coupon.setValidFrom(couponDetails.getValidFrom());
        coupon.setValidUntil(couponDetails.getValidUntil());
        
        return couponRepository.save(coupon);
    }
    
    public void deleteCoupon(Long id) {
        Coupon coupon = couponRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Coupon not found with id: " + id));
        
        couponRepository.delete(coupon);
    }
    
    public boolean validateCoupon(String code, Double orderAmount) {
        Optional<Coupon> couponOpt = couponRepository.findValidCouponByCode(code, LocalDateTime.now());
        
        if (!couponOpt.isPresent()) {
            return false;
        }
        
        Coupon coupon = couponOpt.get();
        
        // Check minimum order amount
        if (orderAmount < coupon.getMinOrderAmount()) {
            return false;
        }
        
        // Check usage limit
        if (coupon.getUsageLimit() != null && coupon.getUsedCount() >= coupon.getUsageLimit()) {
            return false;
        }
        
        return true;
    }
    
    public Double calculateDiscount(String code, Double orderAmount) {
        Optional<Coupon> couponOpt = couponRepository.findValidCouponByCode(code, LocalDateTime.now());
        
        if (!couponOpt.isPresent()) {
            throw new RuntimeException("Invalid coupon code");
        }
        
        Coupon coupon = couponOpt.get();
        
        if (!validateCoupon(code, orderAmount)) {
            throw new RuntimeException("Coupon validation failed");
        }
        
        Double discountAmount = 0.0;
        
        if (coupon.getDiscountType() == Coupon.DiscountType.PERCENTAGE) {
            discountAmount = (orderAmount * coupon.getDiscountValue()) / 100;
            if (coupon.getMaxDiscountAmount() != null && discountAmount > coupon.getMaxDiscountAmount()) {
                discountAmount = coupon.getMaxDiscountAmount();
            }
        } else {
            discountAmount = coupon.getDiscountValue();
        }
        
        return Math.min(discountAmount, orderAmount);
    }
    
    public void useCoupon(String code) {
        Optional<Coupon> couponOpt = couponRepository.findByCode(code);
        
        if (couponOpt.isPresent()) {
            Coupon coupon = couponOpt.get();
            coupon.setUsedCount(coupon.getUsedCount() + 1);
            couponRepository.save(coupon);
        }
    }
}
