package com.ecommerce.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

	public List<Product> findByCategoryOrderByPriceAsc(Category category);

	public List<Product> findByProductNameLikeIgnoreCase(String string);


	
}
