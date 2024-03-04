package com.woodland.serviceImplementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woodland.dto.ProductPicSizesDto;
import com.woodland.dto.ProductPicturesdto;
import com.woodland.dto.ProductsDto;
import com.woodland.entity.ProductPicSizes;
import com.woodland.entity.ProductPictures;
import com.woodland.entity.Products;
import com.woodland.exception.ConversionFailed;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.repository.ProductsRepo;
import com.woodland.service.WoodlandProductsServices;

@Service
public class WoodlandProductsServiceImpl  implements WoodlandProductsServices{

	@Autowired
	private ProductsRepo productsRepo;
	
	/**
	 * Calculates the total price of the product applying the offer
	 * 
	 * @param productdtoPrice  Price of the product
	 * @param productdtoOffers Offer on the product
	 * @return				   Calculates and returns price-(price*offer/100)
	 */
	public Double calculateFinalprice(Double productdtoPrice, Integer productdtoOffers) {
		return productdtoPrice-(productdtoPrice*productdtoOffers/100);
	}
	
	/**
	 * converts the list of products to list of product dto's
	 * 
	 * @param listOfProducts list of products
	 * @return 				 list of productdto
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
	 * { @return list of productsDto }
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
	 * Returns list of ProductsDto matching the filter parameters.
	 * 
	 * @param category		Category to be fetched
	 * @param subcategory	Sub category under the category to be fetched.
	 * @param speficiFor	Customer gender specification either 'Male' or 'Female'
	 * @return				list of  products dto mathching the filter criteria.
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

	/**
	 * Returns list of products matching the category and specific for filters.
	 * 
	 * @param categoryName category name of the product to be fetched
	 * @param specificFor  gender specification of the product to be fetched.
	 * @return			   list of products matching the category and specific for conditions. 
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
	 * returns a list of products matching the subcategory name
	 * 
	 * @param categoryName category name of the products to be fetched.
	 * @return 			   list of productDto's matching the categoryName criteria.
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
