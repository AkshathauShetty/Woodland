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

public interface WoodlandServices {


	
	public CustomerDto registerCustomer(CustomerDto customerDto);
	public CustomerDto updateCustomer(Principal principal,CustomerDto customerDto);
	//public CustomerDto getProfile(Principal principal,CustomerDto customerDto);
	public CustomerDto getProfiles(Principal principal);

	



}