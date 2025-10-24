package com.epinmarketplace.coupon_service.controller;

import com.epinmarketplace.coupon_service.entity.Coupon;
import com.epinmarketplace.coupon_service.service.CouponService;
import com.epinmarketplace.coupon_service.dto.CouponRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class CouponController {
    
    @Autowired
    private CouponService couponService;
    
    @GetMapping
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return ResponseEntity.ok(coupons);
    }
    
    @GetMapping("/active")
    public ResponseEntity<List<Coupon>> getActiveCoupons() {
        List<Coupon> coupons = couponService.getActiveCoupons();
        return ResponseEntity.ok(coupons);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        Optional<Coupon> coupon = couponService.getCouponById(id);
        return coupon.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/code/{code}")
    public ResponseEntity<Coupon> getCouponByCode(@PathVariable String code) {
        Optional<Coupon> coupon = couponService.getCouponByCode(code);
        return coupon.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CouponRequest couponRequest) {
        try {
            Coupon coupon = couponRequest.toCoupon();
            Coupon createdCoupon = couponService.createCoupon(coupon);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCoupon);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCoupon(@PathVariable Long id, @RequestBody CouponRequest couponRequest) {
        try {
            Coupon coupon = couponRequest.toCoupon();
            Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
            return ResponseEntity.ok(updatedCoupon);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) {
        try {
            couponService.deleteCoupon(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/validate")
    public ResponseEntity<?> validateCoupon(@RequestBody Map<String, Object> request) {
        try {
            String code = (String) request.get("code");
            Double orderAmount = ((Number) request.get("orderAmount")).doubleValue();
            
            boolean isValid = couponService.validateCoupon(code, orderAmount);
            if (!isValid) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid coupon code"));
            }
            
            // Get coupon details and calculate discount
            Optional<Coupon> couponOpt = couponService.getCouponByCode(code);
            if (!couponOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Coupon not found"));
            }
            
            Coupon coupon = couponOpt.get();
            Double discountAmount = couponService.calculateDiscount(code, orderAmount);
            
            return ResponseEntity.ok(Map.of(
                "coupon", coupon,
                "discountAmount", discountAmount,
                "valid", true
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/calculate-discount")
    public ResponseEntity<?> calculateDiscount(@RequestBody Map<String, Object> request) {
        try {
            String code = (String) request.get("code");
            Double orderAmount = ((Number) request.get("orderAmount")).doubleValue();
            
            Double discountAmount = couponService.calculateDiscount(code, orderAmount);
            return ResponseEntity.ok(Map.of("discountAmount", discountAmount));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PostMapping("/use/{code}")
    public ResponseEntity<?> useCoupon(@PathVariable String code) {
        try {
            couponService.useCoupon(code);
            return ResponseEntity.ok(Map.of("message", "Coupon used successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
