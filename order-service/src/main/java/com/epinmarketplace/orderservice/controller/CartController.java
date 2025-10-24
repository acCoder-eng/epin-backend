package com.epinmarketplace.orderservice.controller;

import com.epinmarketplace.orderservice.dto.CartItemDto;
import com.epinmarketplace.orderservice.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CartItemDto>> getCartItems(@PathVariable Long userId) {
        List<CartItemDto> cartItems = cartService.getCartItemsByUserId(userId);
        return ResponseEntity.ok(cartItems);
    }
    
    @PostMapping("/add")
    public ResponseEntity<CartItemDto> addToCart(@RequestBody CartRequest request) {
        CartItemDto cartItem = cartService.addToCart(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cartItem);
    }
    
    @PutMapping("/update")
    public ResponseEntity<CartItemDto> updateCartItem(@RequestBody CartRequest request) {
        CartItemDto cartItem = cartService.updateCartItem(request.getUserId(), request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cartItem);
    }
    
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromCart(@RequestParam Long userId, @RequestParam Long productId) {
        cartService.removeFromCart(userId, productId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getCartItemCount(@PathVariable Long userId) {
        long count = cartService.getCartItemCount(userId);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/total-quantity/{userId}")
    public ResponseEntity<Integer> getTotalQuantity(@PathVariable Long userId) {
        Integer total = cartService.getTotalQuantity(userId);
        return ResponseEntity.ok(total);
    }
    
    // Inner class for request body
    public static class CartRequest {
        private Long userId;
        private Long productId;
        private Integer quantity;
        
        // Constructors
        public CartRequest() {}
        
        public CartRequest(Long userId, Long productId, Integer quantity) {
            this.userId = userId;
            this.productId = productId;
            this.quantity = quantity;
        }
        
        // Getters and Setters
        public Long getUserId() {
            return userId;
        }
        
        public void setUserId(Long userId) {
            this.userId = userId;
        }
        
        public Long getProductId() {
            return productId;
        }
        
        public void setProductId(Long productId) {
            this.productId = productId;
        }
        
        public Integer getQuantity() {
            return quantity;
        }
        
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
