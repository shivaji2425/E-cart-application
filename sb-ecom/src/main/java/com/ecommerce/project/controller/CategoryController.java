package com.ecommerce.project.controller;

import com.ecommerce.project.configuration.AppConstants;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDto;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/public/categories")
	public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name="pageNumber",defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
															 @RequestParam(name= "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
															 @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
															 @RequestParam(name = "sortOrder",defaultValue = AppConstants.SORT_ORDER) String sortOrder){
		return new ResponseEntity<CategoryResponse>(categoryService.getAllCategories(pageNumber,pageSize,sortBy,sortOrder),HttpStatus.OK) ;
	}
	
	@PostMapping("/admin/category")
	public ResponseEntity<CategoryDto> addCategory(@Valid @RequestBody CategoryDto categoryDto) {

		return new ResponseEntity<CategoryDto>(categoryService.createCategory(categoryDto),HttpStatus.CREATED);
	}
	@PutMapping("admin/categories/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Long categoryId) {
		return new ResponseEntity<>(categoryService.updateCategory(categoryDto,categoryId),HttpStatus.OK);
	}
	@DeleteMapping("admin/categories/{categoryId}")
	public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long categoryId) {
		return new ResponseEntity<>(categoryService.deleteCategory(categoryId),HttpStatus.OK);
	}
}
