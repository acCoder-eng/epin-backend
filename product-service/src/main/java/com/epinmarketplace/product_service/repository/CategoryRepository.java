package com.epinmarketplace.product_service.repository;

import com.epinmarketplace.product_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    List<Category> findByIsActiveTrueOrderBySortOrderAsc();
    
    List<Category> findByParentIsNullAndIsActiveTrueOrderBySortOrderAsc();
    
    List<Category> findByParentIdAndIsActiveTrueOrderBySortOrderAsc(Long parentId);
    
    Optional<Category> findByNameAndIsActiveTrue(String name);
    
    boolean existsByNameAndIsActiveTrue(String name);
    
    @Query("SELECT c FROM Category c WHERE c.isActive = true AND (c.name LIKE %:keyword% OR c.description LIKE %:keyword%)")
    List<Category> searchByKeyword(@Param("keyword") String keyword);
}
