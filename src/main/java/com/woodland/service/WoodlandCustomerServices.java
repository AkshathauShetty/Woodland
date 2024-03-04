package com.woodland.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.woodland.dto.AddressDto;
import com.woodland.dto.CustomerDto;
import com.woodland.dto.ProductsDto;
import com.woodland.entity.Category;
import com.woodland.entity.Products;
import com.woodland.entity.Subcategory;

import jakarta.persistence.Tuple;

/**
 * Provides customer profile specific services.
 */
public interface WoodlandCustomerServices {

	/**
	 * Registers the customer. 
	 * 
	 * @param	customerDto	CustomerDto object that needs to be registered
	 * @return				Returns the registered customerDto object. Used for generating the token.
	 */
	public CustomerDto registerCustomer(CustomerDto customerDto);
	
	/**
	 * Obtains the customer phone form the principal and updates the customer. 
	 * 
	 * @param principal		The authentication header object.
	 * @param customerDto	CustomerDto object containing the information to be updated.
	 * @return				The updated customerDto object.
	 */
	public CustomerDto updateCustomer(Principal principal,CustomerDto customerDto);
	
	/**
	 * Returns the customerDto object of the registered customer. 
	 * Used to display the customer details on the front end.
	 * 
	 * @param principal	
	 * @return			CustomerDto matching the customer phone fetched from the principal object. 		
	 */
	public CustomerDto getProfiles(Principal principal);

}