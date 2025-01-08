package com.ecommerce.project.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDto;
import com.ecommerce.project.payload.ProductResponse;
import com.ecommerce.project.repository.CategoryRepo;
import com.ecommerce.project.repository.ProductRepo;
import com.ecommerce.project.service.FileService;
import com.ecommerce.project.service.ProductService;

import lombok.Value;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private FileService fileService;
	
	
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public ProductDto addProduct(Long categoryId, ProductDto productDto) {

		Category category = categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("category", "categoryId", categoryId));
				Product product = modelMapper.map(productDto, Product.class);
				product.setImage("default.png");
				product.setCategory(category);
				product.setSpecialPrice(product.getPrice() - (product.getDiscount()*0.01)*product.getPrice());
				
				return modelMapper.map(productRepo.save(product), ProductDto.class);
	}
	@Override
	public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		
		System.out.println(" Inside service implemention ");
		   Sort sortByOrder = sortOrder.equalsIgnoreCase("asc") 
				             ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		  Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByOrder);
		       
			Page<Product> productPage = productRepo.findAll(pageDetails);
			
			List<Product> products = productPage.getContent();
			if(products == null)
				throw new APIException("No category present till now");
			List<ProductDto> productList = products.stream().map(
			        		 product -> modelMapper.map(product, ProductDto.class)
			        		 ).collect(Collectors.toList());
			
			
		    ProductResponse productResponse = new ProductResponse();
		    productResponse.setContent(productList);
		    productResponse.setPageNumber(productPage.getNumber());
		    productResponse.setPageSize(productPage.getSize());
		    productResponse.setTotalElements(productPage.getTotalElements());
		    productResponse.setLastPage(productPage.isLast());
		    productResponse.setSortBy(sortBy);
		    productResponse.setSortOrder(sortOrder);
		    
		    return productResponse;
		           
	}
	@Override
	public ProductResponse getAllProductsByCategory(Long categoryId) {

		Category category = categoryRepo.findById(categoryId).orElseThrow(
				()-> new ResourceNotFoundException("category", "categoryId", categoryId));
		
		        List<Product> products = productRepo.findByCategoryOrderByPriceAsc(category);
		        if(products == null)
					throw new APIException("No category present till now");
		        List<ProductDto> productList = products.stream().map(
		        		 product -> modelMapper.map(product, ProductDto.class)
		        		 ).collect(Collectors.toList());
		
		        ProductResponse productResponse = new ProductResponse();
			    productResponse.setContent(productList);
		        
		        return productResponse;
	}
	@Override
	public ProductResponse searchProductsByKeyword(String keyword) {

		List<Product> products = productRepo.findByProductNameLikeIgnoreCase('%' + keyword + '%');

		List<ProductDto> productList = products.stream().map(
				product -> modelMapper.map(product, ProductDto.class)
				).collect(Collectors.toList());
		
		ProductResponse productResponse = new ProductResponse();
		productResponse.setContent(productList);
		
		return productResponse;
	}
	@Override
	public ProductDto updateProduct(Long productId, ProductDto productDto) {
                  
		Product productFromDb = productRepo.findById(productId).orElseThrow(
				()-> new ResourceNotFoundException("product","productId",productId)
				);
		Product product = modelMapper.map(productDto, Product.class);
		productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setSpecialPrice(product.getSpecialPrice());
        
		return modelMapper.map(productRepo.save(productFromDb), ProductDto.class);
	}
	@Override
	public ProductDto updateProductImage(Long productId, MultipartFile image) throws IOException {
		//Step 1: Get all the products from DB
		 Product product = productRepo.findById(productId).orElseThrow(
				 ()-> new ResourceNotFoundException("product","productId",productId)
				 );
		 String path = "images/";
		 String filename = fileService.uploadImage(path, image);
		 product.setImage(filename);
		 
		//Step 2: Upload image to server
		//Step 3: Get the file name of uploaded image
		//Step 4: Updating the new file name to the product
		return modelMapper.map(productRepo.save(product), ProductDto.class);
	}

}







