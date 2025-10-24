package com.epinmarketplace.product_service.repository;

import com.epinmarketplace.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    Page<Product> findByIsActiveTrue(Pageable pageable);
    
    Page<Product> findByCategoryIdAndIsActiveTrue(Long categoryId, Pageable pageable);
    
    Page<Product> findBySellerIdAndIsActiveTrue(Long sellerId, Pageable pageable);
    
    Page<Product> findByIsFeaturedTrueAndIsActiveTrue(Pageable pageable);
    
    List<Product> findByIsActiveTrueOrderByCreatedAtDesc();
    
    List<Product> findByIsActiveTrueOrderBySalesCountDesc();
    
    List<Product> findByIsActiveTrueOrderByRatingDesc();
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND " +
           "(p.name LIKE %:keyword% OR p.description LIKE %:keyword% OR p.platform LIKE %:keyword%)")
    Page<Product> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.price BETWEEN :minPrice AND :maxPrice")
    Page<Product> findByPriceRange(@Param("minPrice") BigDecimal minPrice, 
                                   @Param("maxPrice") BigDecimal maxPrice, 
                                   Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.stockQuantity > 0")
    Page<Product> findInStock(Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.stockQuantity <= :threshold")
    List<Product> findLowStock(@Param("threshold") Integer threshold);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.rating >= :minRating")
    Page<Product> findByMinRating(@Param("minRating") BigDecimal minRating, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.platform = :platform")
    Page<Product> findByPlatform(@Param("platform") String platform, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.region = :region")
    Page<Product> findByRegion(@Param("region") String region, Pageable pageable);
    
    @Query("SELECT p FROM Product p WHERE p.isActive = true AND p.category.id = :categoryId AND p.stockQuantity > 0")
    List<Product> findAvailableByCategory(@Param("categoryId") Long categoryId);
    
    Optional<Product> findByIdAndIsActiveTrue(Long id);
    
    boolean existsByIdAndSellerId(Long id, Long sellerId);
}
