package com.woodland.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="createSubcatDto")
public class SubcategoryDto {
	private int subCategoryId;
	private String subcategoryName;
	private CategoryDto categoryDto;
	private List<SubcategoryTypesDto> subcategoryTypesDto;

}
