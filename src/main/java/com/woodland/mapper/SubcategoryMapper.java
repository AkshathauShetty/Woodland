package com.woodland.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.woodland.dto.CategoryDto;
import com.woodland.dto.SubcategoryDto;
import com.woodland.dto.SubcategoryTypesDto;
import com.woodland.entity.Subcategory;
import com.woodland.entity.SubcategoryTypes;


@Component
public class SubcategoryMapper {
	
	@Autowired
	SubcategoryTypesMapper subcategoryTypesMapper;

	/**
	 * Convers subcategory to Subcategory dto
	 * @param subcategory
	 * @return SubcategoryDto
	 */
	public SubcategoryDto subcategoryToDto(Subcategory subcategory){
		CategoryDto categoryDto = null;
		List<SubcategoryTypes> subcategoryTypes = subcategory.getSubCategoryTypes();
		List<SubcategoryTypesDto> subcategoryTypesDtos = new ArrayList<>();
		subcategoryTypes.forEach(t -> {
			if(t!=null) {
				subcategoryTypesDtos.add(subcategoryTypesMapper.subcategoryToDto(t));
			}
		});
		SubcategoryDto subcategoryDto=SubcategoryDto.createSubcatDto(subcategory.getSubCategoryId(), subcategory.getSubcategoryName(), null, subcategoryTypesDtos);
		return subcategoryDto;
	}
}
