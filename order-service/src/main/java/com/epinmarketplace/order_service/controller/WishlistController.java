package com.epinmarketplace.order_service.controller;

import com.epinmarketplace.order_service.dto.WishlistItemDto;
import com.epinmarketplace.order_service.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
public class WishlistController {
    
    @Autowired
    private WishlistService wishlistService;
    
    @PostMapping("/add")
    public ResponseEntity<?> addToWishlist(@RequestBody Map<String, Object> wishlistRequest) {
        try {
            Long userId = Long.valueOf(wishlistRequest.get("userId").toString());
            Long productId = Long.valueOf(wishlistRequest.get("productId").toString());
            
            WishlistItemDto wishlistItem = wishlistService.addToWishlist(userId, productId);
            return ResponseEntity.status(HttpStatus.CREATED).body(wishlistItem);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WishlistItemDto>> getWishlistItems(@PathVariable Long userId) {
        List<WishlistItemDto> wishlistItems = wishlistService.getWishlistItems(userId);
        return ResponseEntity.ok(wishlistItems);
    }
    
    @DeleteMapping("/remove")
    public ResponseEntity<?> removeFromWishlist(@RequestParam Long userId, @RequestParam Long productId) {
        try {
            wishlistService.removeFromWishlist(userId, productId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Item removed from wishlist");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<?> clearWishlist(@PathVariable Long userId) {
        try {
            wishlistService.clearWishlist(userId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Wishlist cleared");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Map<String, Long>> getWishlistItemCount(@PathVariable Long userId) {
        Long count = wishlistService.getWishlistItemCount(userId);
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user/{userId}/check/{productId}")
    public ResponseEntity<Map<String, Boolean>> isProductInWishlist(@PathVariable Long userId, @PathVariable Long productId) {
        boolean isInWishlist = wishlistService.isProductInWishlist(userId, productId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("isInWishlist", isInWishlist);
        return ResponseEntity.ok(response);
    }
}
