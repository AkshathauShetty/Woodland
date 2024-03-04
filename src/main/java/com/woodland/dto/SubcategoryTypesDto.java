package com.woodland.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="createSubcatTypeDto")
public class SubcategoryTypesDto {
		private int subCategoryTypeId;
		private String subcategoryTypeName;
}
