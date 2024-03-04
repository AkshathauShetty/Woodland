package com.woodland.service;

import java.util.List;

import com.woodland.dto.ProductsDto;

public interface WoodlandProductsServices {

	public List<ProductsDto> getProducts();
	public List<ProductsDto> getProductonFilter(String category, String subcategory,String speficiFor);
	public List<ProductsDto> getProductByCategory(String categoryName,String specificFor);
	public List<ProductsDto> browseProducts(String subCategoryName);
}
