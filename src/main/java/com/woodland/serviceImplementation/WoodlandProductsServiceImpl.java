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
import com.woodland.exception.ConversionFailed;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.repository.AddressRepo;
import com.woodland.repository.CategoryRepo;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.ProductImageRepo;
import com.woodland.repository.ProductsRepo;
import com.woodland.repository.SubCategoryRepo;
import com.woodland.repository.SubcatTypesRepo;
import com.woodland.service.WoodlandProductsServices;

@Service
public class WoodlandProductsServiceImpl  implements WoodlandProductsServices{

	@Autowired
	private ProductsRepo productsRepo;
	
	public Double calculateFinalprice(Double productdtoPrice, Integer productdtoOffers) {
		return productdtoPrice-(productdtoPrice*productdtoOffers/100);
	}
	
	/**
	 * converts the list of products to list of product dto's
	 * @param list of products
	 * @return list of product dto
	 * name:productsTodto
	 */
	public List<ProductsDto> productsTodto(List<Products> listOfProducts){
		List<ProductsDto> productDto= new ArrayList<>();	
		if(listOfProducts!=null) {
			for(Products product :listOfProducts) {
				// for each of the product create an object of product DTO
				ProductsDto pdto = ProductsDto.create(product.getProductId(), product.getProductPrice(), product.getSuitableFor(), product.getProductOffers(), null,product.getBestSeller(),null,null,null);
				// final price is calculated based on the values of price and offer on the price
				pdto.setProductFinalPrice(calculateFinalprice(pdto.getProductdtoPrice(), pdto.getProductdtoOffers()));
				List<ProductPictures> productPictures = product.getProductPictures();
				List<ProductPicturesdto> productPicturedtolist = new ArrayList<>();
				for(ProductPictures pictures : productPictures) {				
					//ProductPicturesdto productPicturedto = ProductPicturesdto.getPdto(pictures.getProductPicId(), pictures.getProductColor(), pictures.getProductPicUrl());
					// for each picture of the products , create a pictureDto object
					ProductPicturesdto productPicturedto = ProductPicturesdto.createProductPic(pictures.getProductPicId(), pictures.getProductColor(), pictures.getProductPicUrl(),pictures.getProductPicName(),null,null);
					// native query is used to get the list of all the sizes available for the picture
					List<String> prod_sizes = productsRepo.getAllSizes(pictures.getProductPicId());
					// set the list of sizes available in the pictureDto
					productPicturedto.setProductSizes(prod_sizes);
					// for each picture get the sizes of the picttures
					List<ProductPicSizes> productPicSizes = pictures.getProductPictureSizes();
					List<ProductPicSizesDto> productPicSizesDto = new ArrayList<>();
					for(ProductPicSizes picSizes:productPicSizes) {
						// create a dto
						ProductPicSizesDto picsizeDto = ProductPicSizesDto.getPdPicdto(picSizes.getProductPicSizeId(), picSizes.getProductPicSize(),picSizes.getProductPicSizeCount());
						/// save each size dto in the list of size dtos's
						productPicSizesDto.add(picsizeDto);
					}
					// set the picture dto's size mapping to the list of size dto
					productPicturedto.setProductPicSizesdto(productPicSizesDto);
					// add the generated picture dto to the list of pictureDto
					productPicturedtolist.add(productPicturedto);
					
				}
				// set the picture dto mapping of the product to list of picture dto
				pdto.setProductPicturesdto(productPicturedtolist);
				// add the generated product dto to the list of product dto
				productDto.add(pdto);
			}	
			System.out.println("lp done"+productDto);
			if(productDto!=null && !productDto.isEmpty()) {
				return productDto;	
			}
			else {
				throw new DataNotFound("Error occured during converesion");
			}	
		}
		else {
			throw new DataNotFound("No matching results found for the query");
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
			 Map<Subcategory, List<Products>> studlistGrouped =
					listOfProducts.stream().collect(Collectors.groupingBy(w -> w.getSubCategory()));
			 studlistGrouped.forEach((t, u) ->{
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
				 categoryDtoList.add(categoryToDto(t));
			 }
			 );
			 return categoryDtoList;
			 
		}
		else {
			return null;
		}
	}
	
	/**
	 * Convers category to cateory dto
	 * @param category
	 * @return CategoryDto
	 */
	public CategoryDto categoryToDto(Category category){
		List<Subcategory> subcategories = category.getSubCategory();
		List<SubcategoryDto> subcategoryDtos = new ArrayList<>();
		subcategories.forEach(subcategory -> {
			subcategoryDtos.add(subcategoryToDto(subcategory));
		});	
		CategoryDto categoryDto = CategoryDto.categoryDto(category.getCategoryId(), category.getCategoryName(), subcategoryDtos);
		return categoryDto;		
	}
	
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
				subcategoryTypesDtos.add(subcategoryToDto(t));
			}
		});
		SubcategoryDto subcategoryDto=SubcategoryDto.createSubcatDto(subcategory.getSubCategoryId(), subcategory.getSubcategoryName(), null, subcategoryTypesDtos);
		return subcategoryDto;
	}
	
	/**
	 * Convers subcategoryTypes to SubcategoryTypes dto
	 * @param subcategoryTypes
	 * @return SubcategoryTypesDto
	 */
	public SubcategoryTypesDto subcategoryToDto(SubcategoryTypes subcategoryTypes){
		SubcategoryTypesDto subcategoryTypesDto = SubcategoryTypesDto.createSubcatTypeDto(subcategoryTypes.getSubCategoryTypeId(), subcategoryTypes.getSubCategoryType());
		return subcategoryTypesDto;
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
	 *  gives the list of all the products present.
	 * @return list of product dto
	 */
	@Override
	public List<ProductsDto> getProducts() {
		List<Products> productsList= productsRepo.findAll();	
		if(productsList!=null && !productsList.isEmpty()) {
			List<ProductsDto> productDtoList =  productsTodto(productsList);
			if(productDtoList!=null && !productDtoList.isEmpty()) {
				return productDtoList;
			}
			else {
				throw new  ConversionFailed("Operation Failed! try again");
			}
		}
		else {
			throw new DataNotFound("Products Not Found!");
		}
		
	}

	
	/** 
	 *  gives the list of all the products present.
	 * @param none
	 * @return list of product dto
	 */
	@Override
	public List<ProductsDto> getProductonFilter(String subCategory, String subCategoryType,String speficiFor) {
		if(speficiFor!=null && !speficiFor.isEmpty()) {
			if(subCategory!=null && !subCategory.isBlank() && subCategoryType!=null && !subCategoryType.isBlank()) {
				List <Products> products= productsRepo.getProductonFilter(subCategory,subCategoryType,speficiFor);
				return productsTodto(products);
				
			}
			else if(subCategory!=null && !subCategory.isBlank()) {
				List <Products> products= productsRepo.getProductonScatFilter(subCategory,speficiFor);
				return productsTodto(products);
				
			}else {
				List <Products> products = productsRepo.getProds(speficiFor);
				return productsTodto(products);			
			}
		}
		else if(subCategory!=null && !subCategory.isBlank() && subCategoryType!=null && !subCategoryType.isBlank()) {
			List <Products> products= productsRepo.getProductonCatscatFilter(subCategory,subCategoryType);
			return productsTodto(products);
		}
		else if(subCategory!=null && !subCategory.isBlank()) {
			return browseProducts(subCategory);
		}
		else {
			return getProducts();
		}
	}

	 /*  gives the list of all the products present.
	 * @param none
	 * @return list of product dto
	 */
	@Override
	public List<ProductsDto> getProductByCategory(String categoryName,String specificFor) {
		if(specificFor!=null && !specificFor.isEmpty()) {
			if(categoryName!=null && !categoryName.isBlank()) {
				List <Products> products= productsRepo.getProductByCategory(categoryName,specificFor);
				return productsTodto(products);
				
			}
			else {
				List <Products> products = productsRepo.getProds(specificFor);
				return productsTodto(products);			
			}
		}
		else {
			return getProducts();
		}
	}

	
	/** 
	 * returns a list of products matching the search criteria. (subcategory name)
	 * @param  String categoryName
	 * @return list of product dto
	 */
	@Override
	public List<ProductsDto> browseProducts(String subCategoryName) {
		if(subCategoryName!=null && !subCategoryName.isEmpty()) {
			List<Products> productsList = productsRepo.browseProductsBySubcats(subCategoryName);
			if(productsList!=null && !productsList.isEmpty()) {
				return   productsTodto(productsList);
			}
			else {
				 throw new DataNotFound("Search results not found for "+subCategoryName+"!");
			}
		}
		else {
			throw new InputInvalid("Input data required");
		}
		
	}

	
}
