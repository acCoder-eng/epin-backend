package com.epinmarketplace.order_service.service;

import com.epinmarketplace.order_service.client.ProductServiceClient;
import com.epinmarketplace.order_service.dto.ProductDto;
import com.epinmarketplace.order_service.dto.WishlistItemDto;
import com.epinmarketplace.order_service.entity.WishlistItem;
import com.epinmarketplace.order_service.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WishlistService {
    
    @Autowired
    private WishlistItemRepository wishlistItemRepository;
    
    @Autowired
    private ProductServiceClient productServiceClient;
    
    public WishlistItemDto addToWishlist(Long userId, Long productId) {
        // Check if product exists
        try {
            ProductDto product = productServiceClient.getProductById(productId);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
        
        // Check if already in wishlist
        if (wishlistItemRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new RuntimeException("Product already in wishlist");
        }
        
        WishlistItem wishlistItem = new WishlistItem(userId, productId);
        WishlistItem savedItem = wishlistItemRepository.save(wishlistItem);
        return convertToDto(savedItem);
    }
    
    @Transactional(readOnly = true)
    public List<WishlistItemDto> getWishlistItems(Long userId) {
        List<WishlistItem> wishlistItems = wishlistItemRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return wishlistItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public void removeFromWishlist(Long userId, Long productId) {
        wishlistItemRepository.deleteByUserIdAndProductId(userId, productId);
    }
    
    public void clearWishlist(Long userId) {
        wishlistItemRepository.deleteByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public Long getWishlistItemCount(Long userId) {
        return wishlistItemRepository.countByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public boolean isProductInWishlist(Long userId, Long productId) {
        return wishlistItemRepository.existsByUserIdAndProductId(userId, productId);
    }
    
    private WishlistItemDto convertToDto(WishlistItem wishlistItem) {
        WishlistItemDto dto = new WishlistItemDto();
        dto.setId(wishlistItem.getId());
        dto.setUserId(wishlistItem.getUserId());
        dto.setProductId(wishlistItem.getProductId());
        dto.setCreatedAt(wishlistItem.getCreatedAt());
        
        // Fetch product details from Product Service
        try {
            ProductDto product = productServiceClient.getProductById(wishlistItem.getProductId());
            dto.setProductName(product.getName());
            dto.setProductImage(product.getImageUrl());
            dto.setProductPrice(product.getPrice());
            dto.setProductCurrency(product.getCurrency());
            dto.setProductDescription(product.getDescription());
        } catch (Exception e) {
            // Handle case where product service is unavailable
            dto.setProductName("Product unavailable");
            dto.setProductPrice(java.math.BigDecimal.ZERO);
            dto.setProductCurrency("USD");
            dto.setProductDescription("Product details unavailable");
        }
        
        return dto;
    }
}
