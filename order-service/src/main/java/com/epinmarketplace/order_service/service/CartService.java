package com.epinmarketplace.order_service.service;

import com.epinmarketplace.order_service.client.ProductServiceClient;
import com.epinmarketplace.order_service.dto.CartItemDto;
import com.epinmarketplace.order_service.dto.ProductDto;
import com.epinmarketplace.order_service.entity.CartItem;
import com.epinmarketplace.order_service.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    public CartItemDto addToCart(Long userId, Long productId, Integer quantity) {
        // Check if product exists and is available
        try {
            ProductDto product = productServiceClient.getProductById(productId);
            if (product.getStockQuantity() < quantity) {
                throw new RuntimeException("Insufficient stock");
            }
        } catch (Exception e) {
            throw new RuntimeException("Product not found or unavailable");
        }
        
        // Check if item already exists in cart
        Optional<CartItem> existingItemOpt = cartItemRepository.findByUserIdAndProductId(userId, productId);
        
        if (existingItemOpt.isPresent()) {
            // Update quantity
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            CartItem savedItem = cartItemRepository.save(existingItem);
            return convertToDto(savedItem);
        } else {
            // Create new cart item
            CartItem cartItem = new CartItem(userId, productId, quantity);
            CartItem savedItem = cartItemRepository.save(cartItem);
            return convertToDto(savedItem);
        }
    }
    
    @Transactional(readOnly = true)
    public List<CartItemDto> getCartItems(Long userId) {
        List<CartItem> cartItems = cartItemRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return cartItems.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    public CartItemDto updateCartItemQuantity(Long userId, Long productId, Integer quantity) {
        Optional<CartItem> cartItemOpt = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if (cartItemOpt.isEmpty()) {
            throw new RuntimeException("Cart item not found");
        }
        
        if (quantity <= 0) {
            // Remove item from cart
            cartItemRepository.deleteByUserIdAndProductId(userId, productId);
            return null;
        }
        
        // Check stock availability
        try {
            ProductDto product = productServiceClient.getProductById(productId);
            if (product.getStockQuantity() < quantity) {
                throw new RuntimeException("Insufficient stock");
            }
        } catch (Exception e) {
            throw new RuntimeException("Product not found or unavailable");
        }
        
        CartItem cartItem = cartItemOpt.get();
        cartItem.setQuantity(quantity);
        CartItem savedItem = cartItemRepository.save(cartItem);
        return convertToDto(savedItem);
    }
    
    public void removeFromCart(Long userId, Long productId) {
        cartItemRepository.deleteByUserIdAndProductId(userId, productId);
    }
    
    public void clearCart(Long userId) {
        cartItemRepository.deleteByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public Long getCartItemCount(Long userId) {
        return cartItemRepository.countByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public Long getTotalQuantity(Long userId) {
        Long total = cartItemRepository.getTotalQuantityByUserId(userId);
        return total != null ? total : 0L;
    }
    
    @Transactional(readOnly = true)
    public boolean isProductInCart(Long userId, Long productId) {
        return cartItemRepository.findByUserIdAndProductId(userId, productId).isPresent();
    }
    
    private CartItemDto convertToDto(CartItem cartItem) {
        CartItemDto dto = new CartItemDto();
        dto.setId(cartItem.getId());
        dto.setUserId(cartItem.getUserId());
        dto.setProductId(cartItem.getProductId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setCreatedAt(cartItem.getCreatedAt());
        dto.setUpdatedAt(cartItem.getUpdatedAt());
        
        // Fetch product details from Product Service
        try {
            ProductDto product = productServiceClient.getProductById(cartItem.getProductId());
            dto.setProductName(product.getName());
            dto.setProductImage(product.getImageUrl());
            dto.setProductPrice(product.getPrice());
            dto.setProductCurrency(product.getCurrency());
        } catch (Exception e) {
            // Handle case where product service is unavailable
            dto.setProductName("Product unavailable");
            dto.setProductPrice(java.math.BigDecimal.ZERO);
            dto.setProductCurrency("USD");
        }
        
        return dto;
    }
}
