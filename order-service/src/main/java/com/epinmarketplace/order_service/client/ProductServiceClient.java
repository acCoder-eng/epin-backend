package com.epinmarketplace.order_service.client;

import com.epinmarketplace.order_service.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "product-service", fallback = ProductServiceClientFallback.class)
public interface ProductServiceClient {
    
    @GetMapping("/api/products/{id}")
    ProductDto getProductById(@PathVariable Long id);
    
    @PutMapping("/api/products/{id}/stock")
    Map<String, String> updateStock(@PathVariable Long id, @RequestBody Map<String, Integer> stockUpdate);
    
    @GetMapping("/api/products/{id}/available-pins")
    List<Map<String, Object>> getAvailablePinCodes(@PathVariable Long id, @PathVariable Integer quantity);
}
