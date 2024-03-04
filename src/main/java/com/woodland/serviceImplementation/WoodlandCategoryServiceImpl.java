package com.woodland.serviceImplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woodland.dto.CategoryDto;
import com.woodland.dto.ProductsDto;
import com.woodland.dto.SubcategoryDto;
import com.woodland.dto.SubcategoryTypesDto;
import com.woodland.entity.Category;
import com.woodland.entity.Products;
import com.woodland.entity.Subcategory;
import com.woodland.entity.SubcategoryTypes;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.mapper.CategoryMapper;
import com.woodland.repository.ProductsRepo;
import com.woodland.service.WoodlandlandCategoryServices;

@Service
public class WoodlandCategoryServiceImpl implements WoodlandlandCategoryServices{

	@Autowired
	private ProductsRepo productsRepo;
	@Autowired
	private CategoryMapper categoryMapper;

	
	/**
	 * converts the list of products to category DTO's
	 * 
	 * @param listOfProducts  list of products
	 * @return 				  list of categories
	 */
	public List<CategoryDto> productsToCategorydtos(List<Products> listOfProducts){	
		
		List<Category> categoryList = new ArrayList<>();
		List<CategoryDto> categoryDtoList = new ArrayList<>();
		HashMap<Category,List<Subcategory>> categoryMap = new HashMap<>();
		if(listOfProducts!=null) {
			 Map<Subcategory, List<Products>> subcategoryGrouped =
					listOfProducts.stream().collect(Collectors.groupingBy(w -> w.getSubCategory()));
			 subcategoryGrouped.forEach((t, u) ->{
					 List<SubcategoryTypes> subcategoryTypeList =  new ArrayList<>();
					 u.forEach(item->{
					 if(item.getSubCategorytype()!=null) {
						 SubcategoryTypes subcategoryTypes =SubcategoryTypes.createSubcattypes(item.getSubCategorytype().getSubCategoryTypeId(), item.getSubCategorytype().getSubCategoryType(), null, null);
						 if(!subcategoryTypeList.contains(subcategoryTypes)) {
							 subcategoryTypeList.add(subcategoryTypes);
						 } 
					 }
					 });
					 Subcategory subcategory=Subcategory.createSubcategory(t.getSubCategoryId(), t.getSubcategoryName(), null, null, subcategoryTypeList);
					 Category category = Category.createCategory(t.getCategory().getCategoryId(), t.getCategory().getCategoryName(), null);
					 if(!categoryMap.containsKey(category)) {
						 List<Subcategory> subcategoryMap= new ArrayList<>();
						 subcategoryMap.add(subcategory);
						 categoryMap.put(category, subcategoryMap);
					 }
					 else {
						 List<Subcategory> subcategoryMap=categoryMap.get(category);
						 if(!subcategoryMap.contains(subcategory)) {
							 subcategoryMap.add(subcategory);
							 categoryMap.replace(category, subcategoryMap);
						 }
						
					 }
					 });
			 categoryMap.forEach((t, u) -> {
				 t.setSubCategory(u);
				 categoryList.add(t);
				 
			 });
			 
			 categoryList.forEach(t ->{
				 categoryDtoList.add(categoryMapper.categoryToDto(t));
			 }
			 );
			
			 return categoryDtoList;
			 
		}
		else {
			throw new DataNotFound("Data Not found");
		}
	}

	/**
	 * Returns list of categories based on the filter.
	 * 
	 * @param specificFor Customer specific filter containing either "Male" or "Female".
	 * @return			  saved list of categories
	 */
	public List<CategoryDto> getCategoriess(String specificFor) {
		if(!specificFor.isEmpty()) {
			List<Products> productsList = productsRepo.getProds(specificFor);
			if(productsList!=null && !productsList.isEmpty()) {
				return  productsToCategorydtos(productsList);
			}
			else {
				throw new DataNotFound("No categories");
			}
			
		}
		else {
			throw new InputInvalid("Invalid input");
		}
	}


}
