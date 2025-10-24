package com.epinmarketplace.product_service.repository;

import com.epinmarketplace.product_service.entity.EPinCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EPinCodeRepository extends JpaRepository<EPinCode, Long> {
    
    List<EPinCode> findByProductIdAndIsUsedFalse(Long productId);
    
    List<EPinCode> findByProductIdAndIsUsedFalseAndExpiresAtAfter(Long productId, java.time.LocalDateTime now);
    
    Optional<EPinCode> findByPinCodeAndIsUsedFalse(String pinCode);
    
    List<EPinCode> findByOrderItemId(Long orderItemId);
    
    @Query("SELECT e FROM EPinCode e WHERE e.product.id = :productId AND e.isUsed = false ORDER BY e.createdAt ASC")
    List<EPinCode> findAvailableByProductOrderByCreatedAt(@Param("productId") Long productId);
    
    @Query("SELECT COUNT(e) FROM EPinCode e WHERE e.product.id = :productId AND e.isUsed = false")
    Long countAvailableByProduct(@Param("productId") Long productId);
    
    @Query("SELECT COUNT(e) FROM EPinCode e WHERE e.product.id = :productId AND e.isUsed = true")
    Long countUsedByProduct(@Param("productId") Long productId);
    
    @Query("SELECT e FROM EPinCode e WHERE e.product.id = :productId AND e.isUsed = false AND e.expiresAt > :now ORDER BY e.createdAt ASC LIMIT :limit")
    List<EPinCode> findAvailableByProductWithLimit(@Param("productId") Long productId, 
                                                   @Param("now") java.time.LocalDateTime now, 
                                                   @Param("limit") int limit);
}
