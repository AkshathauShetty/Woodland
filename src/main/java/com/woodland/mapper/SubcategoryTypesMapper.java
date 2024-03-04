package com.woodland.mapper;

import org.springframework.stereotype.Component;

import com.woodland.dto.SubcategoryTypesDto;
import com.woodland.entity.SubcategoryTypes;


@Component
public class SubcategoryTypesMapper {
	
	
	/**
	 * Convers subcategoryTypes to SubcategoryTypes dto
	 * @param subcategoryTypes
	 * @return SubcategoryTypesDto
	 */
	public SubcategoryTypesDto subcategoryToDto(SubcategoryTypes subcategoryTypes){
		SubcategoryTypesDto subcategoryTypesDto = SubcategoryTypesDto.createSubcatTypeDto(subcategoryTypes.getSubCategoryTypeId(), subcategoryTypes.getSubCategoryType());
		return subcategoryTypesDto;
	}
		

}
