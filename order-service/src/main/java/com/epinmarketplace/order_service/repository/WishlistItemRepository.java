package com.epinmarketplace.order_service.repository;

import com.epinmarketplace.order_service.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    
    List<WishlistItem> findByUserIdOrderByCreatedAtDesc(Long userId);
    
    Optional<WishlistItem> findByUserIdAndProductId(Long userId, Long productId);
    
    @Query("SELECT w FROM WishlistItem w WHERE w.userId = :userId")
    List<WishlistItem> findByUserId(@Param("userId") Long userId);
    
    @Query("SELECT COUNT(w) FROM WishlistItem w WHERE w.userId = :userId")
    Long countByUserId(@Param("userId") Long userId);
    
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    
    void deleteByUserIdAndProductId(Long userId, Long productId);
    
    void deleteByUserId(Long userId);
}
