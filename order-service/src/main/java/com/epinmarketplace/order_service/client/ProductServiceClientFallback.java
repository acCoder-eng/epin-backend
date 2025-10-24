package com.epinmarketplace.order_service.client;

import com.epinmarketplace.order_service.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductServiceClientFallback implements ProductServiceClient {
    
    @Override
    public ProductDto getProductById(Long id) {
        // Return a default product or throw exception
        throw new RuntimeException("Product service is unavailable");
    }
    
    @Override
    public Map<String, String> updateStock(Long id, Map<String, Integer> stockUpdate) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Product service is unavailable");
        return response;
    }
    
    @Override
    public List<Map<String, Object>> getAvailablePinCodes(Long id, Integer quantity) {
        throw new RuntimeException("Product service is unavailable");
    }
}
