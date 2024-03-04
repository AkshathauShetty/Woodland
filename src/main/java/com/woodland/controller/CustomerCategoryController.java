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

import com.woodland.exception.InputInvalid;
import com.woodland.service.WoodlandlandCategoryServices;


@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerCategoryController {
	
	@Autowired
	WoodlandlandCategoryServices categoryServices;
	

	/**
	 * Returns the category based on the filter name
	 * @param {AddressDto} customerDto - AddressDto containing the address details to be saved.
	 * @return  { ResponseEntity<AddressDto> } - saved address.
	 */
	@GetMapping("/getCategories/{catFilter}")
	public ResponseEntity<?> getCategories(@PathVariable("catFilter") String specificFor) {
		try {
			List results = categoryServices.getCategoriess(specificFor);
			return new ResponseEntity<>(results,HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
	}
}
