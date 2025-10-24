package com.epinmarketplace.coupon_service.repository;

import com.epinmarketplace.coupon_service.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
    Optional<Coupon> findByCode(String code);
    
    List<Coupon> findByIsActiveTrue();
    
    @Query("SELECT c FROM Coupon c WHERE c.isActive = true AND c.validFrom <= :now AND c.validUntil >= :now")
    List<Coupon> findActiveAndValidCoupons(@Param("now") LocalDateTime now);
    
    @Query("SELECT c FROM Coupon c WHERE c.code = :code AND c.isActive = true AND c.validFrom <= :now AND c.validUntil >= :now")
    Optional<Coupon> findValidCouponByCode(@Param("code") String code, @Param("now") LocalDateTime now);
    
    boolean existsByCode(String code);
}
