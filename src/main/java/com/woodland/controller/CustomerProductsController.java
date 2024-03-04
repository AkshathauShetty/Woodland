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
	 * return a list of all the products
	 * @return {ResponseEntity<List<ProductsDto>>} - Http response entity with list of all the products and a http status code
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
	 * return a list of all the products on filter
	 * @return {ResponseEntity<List<ProductsDto>>} - Http response entity with list of all the products and a http status code
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
	 * return a list of all the products on filter
	 * @return {ResponseEntity<List<ProductsDto>>} - Http response entity with list of all the products and a http status code
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
	 * search for a product based on the category filter name
	 * @param {String} categoryFilter - filter name 
	 * @return  {ResponseEntity<List<ProductsDto>>} - Http response entity with list of all the products and a http status code
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
