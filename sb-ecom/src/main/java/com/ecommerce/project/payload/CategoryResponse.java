package com.ecommerce.project.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
   private List<CategoryDto> content;
   private Integer pageNumber;
   private Integer pageSize;
   private Long totalElements;
   private Integer totalpages;
   private boolean lastPage;
   private String sortBy;
   private String sortOrder;
   
}


/*
 * Example for the pagenation: pageNumber = 0 pageSize = 50 totalElements = 11
 * totalpages = 1 lastPage : true
 */