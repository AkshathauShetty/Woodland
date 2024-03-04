package com.woodland.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woodland.dto.ProductsDto;
import com.woodland.exception.DataNotFound;
import com.woodland.service.WoodlandProductsServices;

@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerProductsController {
	
	@Autowired
	WoodlandProductsServices customerProductsServices;
	
	/**
	 * { @return response entity with list of productsDto and ResponseStatus OK}
	 */
	@GetMapping("/getproducts")
	public ResponseEntity<List<ProductsDto>> getProducts() {
		
		
		try {
			List<ProductsDto> productDtoList = customerProductsServices.getProducts();
			return new ResponseEntity<>(productDtoList,HttpStatus.OK);
		}
		catch(DataNotFound e) {
			throw new DataNotFound(e.getMessage());
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * Returns list of ProductsDto matching the filter parameters.
	 * 
	 * @param category		Category to be fetched
	 * @param subcategory	Sub category under the category to be fetched.
	 * @param speficiFor	Customer gender specification either 'Male' or 'Female'
	 * @return				ResponesEntity with a list of  products dto matching the filter criteria and ResponseStatus OK.
	 */
	@GetMapping("/getFilteredProducts/{subCategory}/{subCategoryType}/{specificFor}")
	public ResponseEntity<List<ProductsDto>> getFilteredProducts(@PathVariable("subCategory") String subCategory,@PathVariable("subCategoryType") String subCategoryType,@PathVariable("specificFor") String specificFor){		
	
		try {
			List<ProductsDto> productDtoList = customerProductsServices.getProductonFilter(subCategory,subCategoryType,specificFor);
			return new ResponseEntity<>(productDtoList,HttpStatus.OK);
		}
		catch(DataNotFound e) {
			throw e;
		}
	}
	
	
	/**
	 * Returns list of products matching the category and specific for filters.
	 * 
	 * @param categoryName category name of the product to be fetched
	 * @param specificFor  gender specification of the product to be fetched.
	 * @return			   ReponseEntity with a list of products matching the category and specific for conditions along with the HttpStatus OK.
	 */
	@GetMapping("/getProductsByCategory/{category_name}/{specificFor}")
	public ResponseEntity<List<ProductsDto>> getProductsByCategory(@PathVariable("category_name") String categoryName,@PathVariable("specificFor") String specificFor){		
		try {
			List<ProductsDto> productDtoList = customerProductsServices.getProductByCategory(categoryName,specificFor);	
			return new ResponseEntity<>(productDtoList,HttpStatus.OK);
		}
		catch(DataNotFound e) {
			throw e;
		}
	}
	
	/** 
	 * returns a list of products matching the subcategory name
	 * 
	 * @param categoryName category name of the products to be fetched.
	 * @return 			   list of productDto's matching the categoryName criteria.
	 */
	@GetMapping("/brosweProduct/{categoryFilter}")
	public ResponseEntity<List<ProductsDto>> brosweProduct(@PathVariable("categoryFilter") String categoryFilter) {
		List<ProductsDto> productDtoList=customerProductsServices.browseProducts(categoryFilter);
		if(productDtoList!=null && !productDtoList.isEmpty()) {
			return new ResponseEntity<>(productDtoList,HttpStatus.OK);
		}else {
			// call is success but the query did not return a value - 204 
			return new ResponseEntity<>(productDtoList,HttpStatus.NO_CONTENT);
		}	
	}
	

}
