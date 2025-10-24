package com.epinmarketplace.order_service.controller;

import com.epinmarketplace.order_service.dto.CartItemDto;
import com.epinmarketplace.order_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody Map<String, Object> cartRequest) {
        try {
            Long userId = Long.valueOf(cartRequest.get("userId").toString());
            Long productId = Long.valueOf(cartRequest.get("productId").toString());
            Integer quantity = Integer.valueOf(cartRequest.get("quantity").toString());
            
            CartItemDto cartItem = cartService.addToCart(userId, productId, quantity);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable Long userId) {
        List<CartItemDto> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }
    
    @PutMapping("/update")
    public ResponseEntity<?> updateCartItemQuantity(@RequestBody Map<String, Object> updateRequest) {
        try {
            Long userId = Long.valueOf(updateRequest.get("userId").toString());
            Long productId = Long.valueOf(updateRequest.get("productId").toString());
            Integer quantity = Integer.valueOf(updateRequest.get("quantity").toString());
            
            CartItemDto cartItem = cartService.updateCartItemQuantity(userId, productId, quantity);
            if (cartItem == null) {
                Map<String, String> response = new HashMap<>();
                response.put("message", "Item removed from cart");
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.ok(cartItem);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        try {
            cartService.removeFromCart(userId, productId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Item removed from cart");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        try {
            cartService.clearCart(userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cart cleared");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Long>> getCartItemCount(@PathVariable Long userId) {
        Long count = cartService.getCartItemCount(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user/{userId}/total-quantity")
    public ResponseEntity<Map<String, Long>> getTotalQuantity(@PathVariable Long userId) {
        Long totalQuantity = cartService.getTotalQuantity(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("totalQuantity", totalQuantity);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user/{userId}/check/{productId}")
    public ResponseEntity<Map<String, Boolean>> isProductInCart(@PathVariable Long userId, @PathVariable Long productId) {
        boolean isInCart = cartService.isProductInCart(userId, productId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isInCart", isInCart);
        return ResponseEntity.ok(response);
    }
}
