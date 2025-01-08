package com.ecommerce.project.service;

import java.util.List;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDto;
import com.ecommerce.project.payload.CategoryResponse;

public interface CategoryService {

      public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
      
      public CategoryDto createCategory(CategoryDto categoryDto);

      public CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
    
      public CategoryDto deleteCategory(Long categoryId);
     
}
