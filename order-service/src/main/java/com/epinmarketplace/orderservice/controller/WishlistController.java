package com.epinmarketplace.orderservice.controller;

import com.epinmarketplace.orderservice.dto.WishlistItemDto;
import com.epinmarketplace.orderservice.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistItemDto>> getWishlistItems(@PathVariable Long userId) {
        List<WishlistItemDto> wishlistItems = wishlistService.getWishlistItemsByUserId(userId);
        return ResponseEntity.ok(wishlistItems);
    }
    
    @PostMapping("/add")
    public ResponseEntity<WishlistItemDto> addToWishlist(@RequestBody WishlistRequest request) {
        WishlistItemDto wishlistItem = wishlistService.addToWishlist(request.getUserId(), request.getProductId());
        return ResponseEntity.ok(wishlistItem);
    }
    
    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeFromWishlist(@RequestParam Long userId, @RequestParam Long productId) {
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearWishlist(@PathVariable Long userId) {
        wishlistService.clearWishlist(userId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/count/{userId}")
    public ResponseEntity<Long> getWishlistItemCount(@PathVariable Long userId) {
        long count = wishlistService.getWishlistItemCount(userId);
        return ResponseEntity.ok(count);
    }
    
    @GetMapping("/check/{userId}/{productId}")
    public ResponseEntity<Boolean> isInWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        boolean isInWishlist = wishlistService.isInWishlist(userId, productId);
        return ResponseEntity.ok(isInWishlist);
    }
    
    // Inner class for request body
    public static class WishlistRequest {
        private Long userId;
        private Long productId;
        
        // Constructors
        public WishlistRequest() {}
        
        public WishlistRequest(Long userId, Long productId) {
            this.userId = userId;
            this.productId = productId;
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
    }
}
