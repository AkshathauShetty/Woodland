package com.woodland.dto;

import java.util.Date;
import java.util.List;

import com.woodland.entity.Subcategory;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="categoryDto")
public class CategoryDto {
	private int categoryId;
	private String categoryName;	
	private List<SubcategoryDto> subCategoryDto;

}
