package com.epinmarketplace.order_service.repository;

import com.epinmarketplace.order_service.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    
    List<CartItem> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
    
    @Query("SELECT c FROM CartItem c WHERE c.userId = :userId")
    List<CartItem> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(c) FROM CartItem c WHERE c.userId = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    @Query("SELECT SUM(c.quantity) FROM CartItem c WHERE c.userId = :userId")
    Long getTotalQuantityByUserId(@Param("userId") Long userId);
    
    void deleteByUserIdAndProductId(Long userId, Long productId);
    
    void deleteByUserId(Long userId);
}
