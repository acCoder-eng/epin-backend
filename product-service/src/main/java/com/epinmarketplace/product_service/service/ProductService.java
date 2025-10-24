package com.epinmarketplace.product_service.service;

import com.epinmarketplace.product_service.dto.ProductDto;
import com.epinmarketplace.product_service.entity.Category;
import com.epinmarketplace.product_service.entity.EPinCode;
import com.epinmarketplace.product_service.entity.Product;
import com.epinmarketplace.product_service.repository.CategoryRepository;
import com.epinmarketplace.product_service.repository.EPinCodeRepository;
import com.epinmarketplace.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private EPinCodeRepository ePinCodeRepository;
    
    public ProductDto createProduct(ProductDto productDto) {
        Optional<Category> categoryOpt = categoryRepository.findById(productDto.getCategoryId());
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        
        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(categoryOpt.get());
        product.setSellerId(productDto.getSellerId());
        product.setPrice(productDto.getPrice());
        product.setOriginalPrice(productDto.getOriginalPrice());
        product.setCurrency(productDto.getCurrency());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setMinQuantity(productDto.getMinQuantity());
        product.setMaxQuantity(productDto.getMaxQuantity());
        product.setImageUrl(productDto.getImageUrl());
        product.setPinFormat(productDto.getPinFormat());
        product.setPinType(productDto.getPinType());
        product.setRegion(productDto.getRegion());
        product.setPlatform(productDto.getPlatform());
        product.setIsActive(productDto.getIsActive());
        product.setIsFeatured(productDto.getIsFeatured());
        
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findByIsActiveTrue(pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByCategory(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndIsActiveTrue(categoryId, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsBySeller(Long sellerId, Pageable pageable) {
        return productRepository.findBySellerIdAndIsActiveTrue(sellerId, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> getFeaturedProducts(Pageable pageable) {
        return productRepository.findByIsFeaturedTrueAndIsActiveTrue(pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public ProductDto getProductById(Long id) {
        Optional<Product> productOpt = productRepository.findByIdAndIsActiveTrue(id);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        return convertToDto(productOpt.get());
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> searchProducts(String keyword, Pageable pageable) {
        return productRepository.searchByKeyword(keyword, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        return productRepository.findByPriceRange(minPrice, maxPrice, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> getInStockProducts(Pageable pageable) {
        return productRepository.findInStock(pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByPlatform(String platform, Pageable pageable) {
        return productRepository.findByPlatform(platform, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByRegion(String region, Pageable pageable) {
        return productRepository.findByRegion(region, pageable)
                .map(this::convertToDto);
    }
    
    @Transactional(readOnly = true)
    public Page<ProductDto> getProductsByMinRating(BigDecimal minRating, Pageable pageable) {
        return productRepository.findByMinRating(minRating, pageable)
                .map(this::convertToDto);
    }
    
    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        Product product = productOpt.get();
        
        // Update fields
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setOriginalPrice(productDto.getOriginalPrice());
        product.setCurrency(productDto.getCurrency());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setMinQuantity(productDto.getMinQuantity());
        product.setMaxQuantity(productDto.getMaxQuantity());
        product.setImageUrl(productDto.getImageUrl());
        product.setPinFormat(productDto.getPinFormat());
        product.setPinType(productDto.getPinType());
        product.setRegion(productDto.getRegion());
        product.setPlatform(productDto.getPlatform());
        product.setIsActive(productDto.getIsActive());
        product.setIsFeatured(productDto.getIsFeatured());
        
        // Update category if provided
        if (productDto.getCategoryId() != null) {
            Optional<Category> categoryOpt = categoryRepository.findById(productDto.getCategoryId());
            if (categoryOpt.isPresent()) {
                product.setCategory(categoryOpt.get());
            }
        }
        
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }
    
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
    }
    
    public void deactivateProduct(Long id) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        Product product = productOpt.get();
        product.setIsActive(false);
        productRepository.save(product);
    }
    
    public void updateStock(Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        Product product = productOpt.get();
        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);
    }
    
    public void updateRating(Long productId, BigDecimal newRating, Integer reviewCount) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        Product product = productOpt.get();
        product.setRating(newRating);
        product.setReviewCount(reviewCount);
        productRepository.save(product);
    }
    
    public void incrementSalesCount(Long productId, Integer quantity) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        
        Product product = productOpt.get();
        product.setSalesCount(product.getSalesCount() + quantity);
        productRepository.save(product);
    }
    
    @Transactional(readOnly = true)
    public List<EPinCode> getAvailablePinCodes(Long productId, Integer quantity) {
        LocalDateTime now = LocalDateTime.now();
        return ePinCodeRepository.findAvailableByProductWithLimit(productId, now, quantity);
    }
    
    public void markPinCodesAsUsed(List<Long> pinCodeIds, Long orderItemId) {
        List<EPinCode> pinCodes = ePinCodeRepository.findAllById(pinCodeIds);
        for (EPinCode pinCode : pinCodes) {
            pinCode.setIsUsed(true);
            pinCode.setUsedAt(LocalDateTime.now());
            pinCode.setOrderItemId(orderItemId);
        }
        ePinCodeRepository.saveAll(pinCodes);
    }
    
    @Transactional(readOnly = true)
    public List<ProductDto> getLowStockProducts(Integer threshold) {
        return productRepository.findLowStock(threshold).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategoryId(product.getCategory().getId());
        dto.setSellerId(product.getSellerId());
        dto.setPrice(product.getPrice());
        dto.setOriginalPrice(product.getOriginalPrice());
        dto.setCurrency(product.getCurrency());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setMinQuantity(product.getMinQuantity());
        dto.setMaxQuantity(product.getMaxQuantity());
        dto.setImageUrl(product.getImageUrl());
        dto.setPinFormat(product.getPinFormat());
        dto.setPinType(product.getPinType());
        dto.setRegion(product.getRegion());
        dto.setPlatform(product.getPlatform());
        dto.setIsActive(product.getIsActive());
        dto.setIsFeatured(product.getIsFeatured());
        dto.setRating(product.getRating());
        dto.setReviewCount(product.getReviewCount());
        dto.setSalesCount(product.getSalesCount());
        
        return dto;
    }
}
