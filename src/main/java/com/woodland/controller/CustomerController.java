package com.woodland.controller;


import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.woodland.dto.AddressDto;
import com.woodland.dto.CustomerDto;
import com.woodland.dto.ProductsDto;
import com.woodland.entity.Category;
import com.woodland.entity.Products;
import com.woodland.entity.Subcategory;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.security.JwtService;
import com.woodland.service.WoodlandServices;

import jakarta.persistence.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.web.bind.annotation.*; 


@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerController {
	
	@Autowired
	private WoodlandServices woodlandServices;
	
	/**
	 * register the customer if not registered else login.
	 * @param {CustomerDto} categoryFilter - filter name 
	 * @return  {ResponseEntity<CustomerDto>} - Http response entity with list of all the products and a http status code
	 */
	@PostMapping("/register")
	public ResponseEntity<CustomerDto> register(@RequestBody CustomerDto customerDto) {
		
		try{
			CustomerDto result=woodlandServices.registerCustomer(customerDto);
			if(result!=null) {
				return new ResponseEntity<>(result,HttpStatus.OK);
			}
			else {
				throw new DataNotFound("No data");
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * update the customer based on the input values
	 * @param {CustomerDto} customerDto - customer dto object  containing the values to be updated. 
	 * @return  {ResponseEntity<CustomerDto>} - Http response entity with the updated customerdto resultand a http status code
	 */
	@PostMapping("/update")
	public ResponseEntity<CustomerDto> update(Principal principal, @RequestBody CustomerDto customerDto) {
		try {
			CustomerDto customerDtoResult =woodlandServices.updateCustomer(principal,customerDto);
			return new ResponseEntity<>(customerDtoResult,HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
			

	}
	
	/**
	 * return the customer profile
	 * @param {CustomerDto} customerDto - customer dto object  containing the values to be updated. 
	 * @return  {ResponseEntity<CustomerDto>} - Http response entity with the updated customerdto result and a http status code
	 */
	@GetMapping("/getProfile")
	public ResponseEntity<CustomerDto> getProfile(Principal principal) {
		try {
			CustomerDto customerDtoResult=woodlandServices.getProfiles(principal);
			return new ResponseEntity<>(customerDtoResult,HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
		
		
	}
}
