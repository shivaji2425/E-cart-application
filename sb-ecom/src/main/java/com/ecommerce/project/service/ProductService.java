package com.ecommerce.project.service;


import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.payload.ProductDto;
import com.ecommerce.project.payload.ProductResponse;

public interface ProductService {

	public ProductDto addProduct(Long categoryId, ProductDto productDto);

    
	public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);


	public ProductResponse getAllProductsByCategory(Long catergoryId);


	public ProductResponse searchProductsByKeyword(String keyword);


	public ProductDto updateProduct(Long productId, ProductDto productDto);


	public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException;
	
}
