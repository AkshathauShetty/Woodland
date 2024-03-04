package com.woodland.serviceImplementation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.woodland.dto.CategoryDto;
import com.woodland.dto.ProductPicSizesDto;
import com.woodland.dto.ProductPicturesdto;
import com.woodland.dto.ProductsDto;
import com.woodland.dto.SubcategoryDto;
import com.woodland.dto.SubcategoryTypesDto;
import com.woodland.entity.Category;
import com.woodland.entity.ProductPicSizes;
import com.woodland.entity.ProductPictures;
import com.woodland.entity.Products;
import com.woodland.entity.Subcategory;
import com.woodland.entity.SubcategoryTypes;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.mapper.CategoryMapper;
import com.woodland.mapper.SubcategoryMapper;
import com.woodland.repository.AddressRepo;
import com.woodland.repository.CategoryRepo;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.ProductImageRepo;
import com.woodland.repository.ProductsRepo;
import com.woodland.repository.SubCategoryRepo;
import com.woodland.repository.SubcatTypesRepo;
import com.woodland.service.WoodlandlandCategoryServices;

@Service
public class WoodlandCategoryServiceImpl implements WoodlandlandCategoryServices{

	@Autowired
	private ProductsRepo productsRepo;
	@Autowired
	private CategoryMapper categoryMapper;
	


	public Double calculateFinalprice(Double productdtoPrice, Integer productdtoOffers) {
		return productdtoPrice-(productdtoPrice*productdtoOffers/100);
	}
	
	

	/**
	 * converts the list of products to category dto's with all subcategories
	 * @param list of products
	 * @return list of categories
	 * name:productsTodto
	 */
	public List<CategoryDto> productsToCategorydto(List<Products> listOfProducts){
		List<ProductsDto> productDto= new ArrayList<>();	
		if(listOfProducts!=null) {
			List<CategoryDto> categoryDtoList = new ArrayList<>();
			for(Products product :listOfProducts) {
				Category category = product.getSubCategory().getCategory();
				List<Subcategory> subcategoryList = category.getSubCategory();
				List<SubcategoryDto> subcategoryDtoList = new ArrayList<>();
				subcategoryList.forEach(subcategory->{
					List<SubcategoryTypes> subcategoryTypeList =subcategory.getSubCategoryTypes();
					List<SubcategoryTypesDto> subcategoryTypeDtoList =  new ArrayList<>();
					subcategoryTypeList.forEach(subcategoryType->{
						SubcategoryTypesDto subcategoryTypesDto = SubcategoryTypesDto.createSubcatTypeDto(subcategoryType.getSubCategoryTypeId(), subcategoryType.getSubCategoryType());
						subcategoryTypeDtoList.add(subcategoryTypesDto);
					});
					SubcategoryDto subcategoryDto = SubcategoryDto.createSubcatDto(subcategory.getSubCategoryId(), subcategory.getSubcategoryName(), null, subcategoryTypeDtoList);
					subcategoryDtoList.add(subcategoryDto);
				});
				CategoryDto categoryDto = CategoryDto.categoryDto(category.getCategoryId(), category.getCategoryName(), subcategoryDtoList);
				if(!categoryDtoList.contains(categoryDto)) {
					categoryDtoList.add(categoryDto);
				}
			}
			return categoryDtoList;
		}
		else {
			return null;
		}
		
	}
	

	
	/**
	 * converts the list of products to category dto's
	 * @param list of products
	 * @return list of categories
	 * name:productsTodto
	 */
	public List<CategoryDto> productsToCategorydtos(List<Products> listOfProducts){	
		List<Category> categoryList = new ArrayList<>();
		List<CategoryDto> categoryDtoList = new ArrayList<>();

		HashMap<Category,List<Subcategory>> categoryMap = new HashMap<>();
		if(listOfProducts!=null) {
			 Map<Subcategory, List<Products>> subcategoryGrouped =
					listOfProducts.stream().collect(Collectors.groupingBy(w -> w.getSubCategory()));
			 subcategoryGrouped.forEach((t, u) ->{
				 System.out.println("inside the catgrup");
					 List<SubcategoryTypes> subcategoryTypeList =  new ArrayList<>();
					 u.forEach(item->{
						 System.out.println("inside the scatgrup");
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
			 System.out.println("outside  the catgrup");
			 categoryMap.forEach((t, u) -> {
				 t.setSubCategory(u);
				 categoryList.add(t);
				 
			 });
			 System.out.println("catmap done");
			 
			 categoryList.forEach(t ->{
				 categoryDtoList.add(categoryMapper.categoryToDto(t));
			 }
			 );
			 System.out.println("catListmap  done");
			 
			 return categoryDtoList;
			 
		}
		else {
			return null;
		}
	}
	

	/**
	 * Returns the categories list containing subcategries in which the specific products are present.
	 * @param {String} specificFor - Men / women specific products
	 * @return  { List<CategoryDto> } - List of categories..
	 */
	@Override
	public List<CategoryDto> getCategories(String specificFor) {
//		if(!specificFor.isEmpty()) {
//			List<Products> productsList = productsRepo.getProds(specificFor);
//			if(productsList!=null && !productsList.isEmpty()) {
//				List<CategoryDto> categoryDtos =  productsToCategorydto(productsList);
//				return categoryDtos;
//			}
//			else {
//				return  null;
//			}
//			
//		}
//		else {
//			return null;
//		}
		return null;
	}
	
	/**
	 * Returns the categories list containing subcategries in which the specific products are present.
	 * @param {String} specificFor - Men / women specific products
	 * @return  { List<CategoryDto> } - List of categories..
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
