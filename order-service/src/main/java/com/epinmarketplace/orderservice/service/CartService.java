package com.epinmarketplace.orderservice.service;

import com.epinmarketplace.orderservice.client.ProductServiceClient;
import com.epinmarketplace.orderservice.dto.CartItemDto;
import com.epinmarketplace.orderservice.dto.ProductDto;
import com.epinmarketplace.orderservice.entity.CartItem;
import com.epinmarketplace.orderservice.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {
    
    @Autowired
    private CartItemRepository cartItemRepository;
    
    @Autowired
    private ProductServiceClient productServiceClient;
    
    public List<CartItemDto> getCartItemsByUserId(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        return cartItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public CartItemDto addToCart(Long userId, Long productId, Integer quantity) {
        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
            return convertToDto(cartItemRepository.save(item));
        } else {
            CartItem newItem = new CartItem(userId, productId, quantity);
            return convertToDto(cartItemRepository.save(newItem));
        }
    }
    
    public CartItemDto updateCartItem(Long userId, Long productId, Integer quantity) {
        Optional<CartItem> existingItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(quantity);
            return convertToDto(cartItemRepository.save(item));
        } else {
            throw new RuntimeException("Cart item not found");
        }
    }
    
    public void removeFromCart(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }
    
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
    
    public long getCartItemCount(Long userId) {
        return cartItemRepository.countByUserId(userId);
    }
    
    public Integer getTotalQuantity(Long userId) {
        Integer total = cartItemRepository.sumQuantityByUserId(userId);
        return total != null ? total : 0;
    }
    
    private CartItemDto convertToDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setId(cartItem.getId());
        dto.setUserId(cartItem.getUserId());
        dto.setProductId(cartItem.getProductId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setCreatedAt(cartItem.getCreatedAt());
        dto.setUpdatedAt(cartItem.getUpdatedAt());
        
        // Fetch product details using Feign Client
        try {
            ProductDto productDto = productServiceClient.getProductById(cartItem.getProductId());
            dto.setProduct(productDto);
        } catch (Exception e) {
            // Handle case where product service is unavailable or product not found
            System.err.println("Error fetching product details for productId: " + cartItem.getProductId() + " - " + e.getMessage());
            // Optionally, set a default or partial product DTO
            ProductDto defaultProduct = new ProductDto();
            defaultProduct.setId(cartItem.getProductId());
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
