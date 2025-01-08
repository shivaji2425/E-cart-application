package com.ecommerce.project.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.configuration.AppConstants;
import com.ecommerce.project.payload.ProductDto;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.service.ProductService;


@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("/admin/categories/{categoryId}/product")
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto, @PathVariable Long categoryId) {

		return new ResponseEntity<>(productService.addProduct(categoryId, productDto), HttpStatus.CREATED);

	}

	@GetMapping("/public/products")
	public ResponseEntity<ProductResponse> getAllProducts(
			@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_BY) String sortBy,
			@RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER) String sortOrder) {
		ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);

		System.out.println("product response " + productResponse);
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	@GetMapping("/public/categories/{catergoryId}/products")
	public ResponseEntity<ProductResponse> getAllProductsByCategory(@PathVariable Long catergoryId) {

		ProductResponse productResponse = productService.getAllProductsByCategory(catergoryId);
		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.OK);
	}

	@GetMapping("/public/products/keyword/{keyword}")
	public ResponseEntity<ProductResponse> searchProductsByKeyword(@PathVariable String keyword) {

		ProductResponse productResponse = productService.searchProductsByKeyword(keyword);

		return new ResponseEntity<ProductResponse>(productResponse, HttpStatus.FOUND);

	}

	@PutMapping("products/{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, @PathVariable Long productId) {

		productService.updateProduct(productId, productDto);

		return new ResponseEntity<ProductDto>(productService.updateProduct(productId, productDto), HttpStatus.OK);

	}
	
	@PutMapping("products/{productId}/image")
	public ResponseEntity<ProductDto> updateProductImage(@PathVariable Long productId, @RequestParam("image") MultipartFile image) throws IOException{
		
		ProductDto productDto = productService.updateProductImage(productId,image);
		
		return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
	}
	
}








