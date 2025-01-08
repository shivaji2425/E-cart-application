package com.ecommerce.project.serviceImpl;


import java.util.List;
import java.util.stream.Collectors;


import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.payload.CategoryDto;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repository.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import com.ecommerce.project.model.Category;
import com.ecommerce.project.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder) {

		Sort sortByOrder = sortOrder.equalsIgnoreCase("asc")
				? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByOrder);
		Page<Category> categoryPage = categoryRepo.findAll(pageDetails);
		List<Category> categories = categoryPage.getContent();
		if(categories == null)
			throw new APIException("No category present till now");
		List<CategoryDto> CategoryList = categories.stream()
				.map(category -> modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
		
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(CategoryList);
		categoryResponse.setPageNumber(categoryPage.getNumber());
		categoryResponse.setPageSize(categoryPage.getSize());
		categoryResponse.setTotalElements(categoryPage.getTotalElements());
		categoryResponse.setTotalpages(categoryPage.getTotalPages());
		categoryResponse.setLastPage(categoryPage.isLast());
		categoryResponse.setSortBy(sortBy);
		categoryResponse.setSortOrder(sortOrder);
		return categoryResponse;
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {

		Category category = modelMapper.map(categoryDto, Category.class);
		Category categoryFromDb = categoryRepo.findByCategoryName(category.getCategoryName());
		if (categoryFromDb != null)
			throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");
			Category savedCategory = categoryRepo.save(category);
			return modelMapper.map(savedCategory, CategoryDto.class);

	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId) {
		Category cate = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
		cate.setCategoryName(categoryDto.getCategoryName());
		Category updatedCategory = categoryRepo.save(cate);
		return modelMapper.map(updatedCategory,CategoryDto.class);
	}

	@Override
	public CategoryDto deleteCategory(Long categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
	               categoryRepo.deleteById(categoryId);
		return modelMapper.map(category,CategoryDto.class);
	}

}
