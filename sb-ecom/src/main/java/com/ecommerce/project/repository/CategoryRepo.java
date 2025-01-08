package com.ecommerce.project.repository;

import com.ecommerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Long> {

    Category findByCategoryName(String name);
}
