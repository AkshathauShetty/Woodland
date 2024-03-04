package com.woodland.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woodland.dto.CategoryDto;
import com.woodland.dto.SubcategoryDto;
import com.woodland.entity.Category;
import com.woodland.entity.Subcategory;

@Component
public class CategoryMapper {
	
	@Autowired
	SubcategoryMapper subcategoryMapper;
	
	/**
	 * Convers category to cateory dto
	 * @param category
	 * @return CategoryDto
	 */
	public CategoryDto categoryToDto(Category category){
		
		List<Subcategory> subcategories = category.getSubCategory();
		List<SubcategoryDto> subcategoryDtos = new ArrayList<>();
		subcategories.forEach(subcategory -> {
			subcategoryDtos.add(subcategoryMapper.subcategoryToDto(subcategory));
		});	
		CategoryDto categoryDto = CategoryDto.categoryDto(category.getCategoryId(), category.getCategoryName(), subcategoryDtos);
		return categoryDto;		
	}

}
