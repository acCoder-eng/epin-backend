package com.epinmarketplace.orderservice.service;

import com.epinmarketplace.orderservice.client.ProductServiceClient;
import com.epinmarketplace.orderservice.dto.ProductDto;
import com.epinmarketplace.orderservice.dto.WishlistItemDto;
import com.epinmarketplace.orderservice.entity.WishlistItem;
import com.epinmarketplace.orderservice.repository.WishlistItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    
    public List<WishlistItemDto> getWishlistItemsByUserId(Long userId) {
        List<WishlistItem> wishlistItems = wishlistItemRepository.findByUserId(userId);
        return wishlistItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public WishlistItemDto addToWishlist(Long userId, Long productId) {
        Optional<WishlistItem> existingItem = wishlistItemRepository.findByUserIdAndProductId(userId, productId);
        
        if (existingItem.isPresent()) {
            return convertToDto(existingItem.get());
        } else {
            WishlistItem newItem = new WishlistItem(userId, productId);
            return convertToDto(wishlistItemRepository.save(newItem));
        }
    }
    
    public void removeFromWishlist(Long userId, Long productId) {
        wishlistItemRepository.deleteByUserIdAndProductId(userId, productId);
    }
    
    public void clearWishlist(Long userId) {
        wishlistItemRepository.deleteByUserId(userId);
    }
    
    public long getWishlistItemCount(Long userId) {
        return wishlistItemRepository.countByUserId(userId);
    }
    
    public boolean isInWishlist(Long userId, Long productId) {
        return wishlistItemRepository.existsByUserIdAndProductId(userId, productId);
    }
    
    private WishlistItemDto convertToDto(WishlistItem wishlistItem) {
        WishlistItemDto dto = new WishlistItemDto();
        dto.setId(wishlistItem.getId());
        dto.setUserId(wishlistItem.getUserId());
        dto.setProductId(wishlistItem.getProductId());
        dto.setCreatedAt(wishlistItem.getCreatedAt());
        
        // Fetch product details using Feign Client
        try {
            ProductDto productDto = productServiceClient.getProductById(wishlistItem.getProductId());
            dto.setProduct(productDto);
        } catch (Exception e) {
            // Handle case where product service is unavailable or product not found
            System.err.println("Error fetching product details for productId: " + wishlistItem.getProductId() + " - " + e.getMessage());
            // Optionally, set a default or partial product DTO
            ProductDto defaultProduct = new ProductDto();
            defaultProduct.setId(wishlistItem.getProductId());
            defaultProduct.setName("Unknown Product");
            defaultProduct.setDescription("N/A");
            defaultProduct.setPrice(0.0);
            defaultProduct.setCurrency("USD");
            defaultProduct.setPlatform("N/A");
            defaultProduct.setStockQuantity(0);
            defaultProduct.setMinQuantity(0);
            dto.setProduct(defaultProduct);
        }
        
        return dto;
    }
}
