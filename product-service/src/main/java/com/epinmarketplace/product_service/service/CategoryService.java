package com.epinmarketplace.product_service.service;

import com.epinmarketplace.product_service.dto.CategoryDto;
import com.epinmarketplace.product_service.entity.Category;
import com.epinmarketplace.product_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setIconUrl(categoryDto.getIconUrl());
        category.setIsActive(categoryDto.getIsActive());
        category.setSortOrder(categoryDto.getSortOrder());
        
        if (categoryDto.getParentId() != null) {
            Optional<Category> parentOpt = categoryRepository.findById(categoryDto.getParentId());
            if (parentOpt.isPresent()) {
                category.setParent(parentOpt.get());
            }
        }
        
        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }
    
    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findByIsActiveTrueOrderBySortOrderAsc().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CategoryDto> getParentCategories() {
        return categoryRepository.findByParentIsNullAndIsActiveTrueOrderBySortOrderAsc().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<CategoryDto> getSubCategories(Long parentId) {
        return categoryRepository.findByParentIdAndIsActiveTrueOrderBySortOrderAsc(parentId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        return convertToDto(categoryOpt.get());
    }
    
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        
        Category category = categoryOpt.get();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setIconUrl(categoryDto.getIconUrl());
        category.setIsActive(categoryDto.getIsActive());
        category.setSortOrder(categoryDto.getSortOrder());
        
        if (categoryDto.getParentId() != null) {
            Optional<Category> parentOpt = categoryRepository.findById(categoryDto.getParentId());
            if (parentOpt.isPresent()) {
                category.setParent(parentOpt.get());
            }
        } else {
            category.setParent(null);
        }
        
        Category savedCategory = categoryRepository.save(category);
        return convertToDto(savedCategory);
    }
    
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
    
    public void deactivateCategory(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            throw new RuntimeException("Category not found");
        }
        
        Category category = categoryOpt.get();
        category.setIsActive(false);
        categoryRepository.save(category);
    }
    
    @Transactional(readOnly = true)
    public List<CategoryDto> searchCategories(String keyword) {
        return categoryRepository.searchByKeyword(keyword).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setIconUrl(category.getIconUrl());
        dto.setIsActive(category.getIsActive());
        dto.setSortOrder(category.getSortOrder());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        
        if (category.getParent() != null) {
            dto.setParentId(category.getParent().getId());
        }
        
        return dto;
    }
}
