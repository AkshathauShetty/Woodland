package com.woodland.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woodland.dto.CustomerDto;
import com.woodland.exception.DataNotFound;
import com.woodland.service.WoodlandCustomerServices;


@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerController {
	
	@Autowired
	private WoodlandCustomerServices woodlandServices;
	
	/**
	 * Registers a new customer and logs in the already registered customer
	 * 
	 * @param categoryFilter filter name 
	 * @return				 HttpResponse entity with the customerDto of the registered customer and HttpStatus.OK status code.
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
	 * Obtains the customer phone form the principal and updates the customer. 
	 * 
	 * @param principal		The authentication header object.
	 * @param customerDto	CustomerDto object containing the information to be updated.
	 * @return				HttpResponse entity with the customerDto of the updated customer and HttpStatus.OK status code.
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
	 * Returns the customerDto object of the registered customer. 
	 * Used to display the customer details on the front end.
	 * 
	 * @param principal	
	 * @return			HttpResponse entity with the customerDto of the requested customer and HttpStatus.OK status code.
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
