package com.ecommerce.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	@NotNull
	@Size(min = 3, message = "Product name must contain atleast 3 characters")
	private String productName;
	private String image;
	@NotNull
	@Size(min = 6, message = "Product description must contain atleast 6 characters")
	private String description;
	
	private Integer quantity;
	private double price;
	private double discount;
	private double specialPrice;
	
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
}







