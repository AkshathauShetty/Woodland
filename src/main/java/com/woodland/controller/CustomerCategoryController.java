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

import com.woodland.dto.CategoryDto;
import com.woodland.service.WoodlandlandCategoryServices;


@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerCategoryController {
	
	@Autowired
	WoodlandlandCategoryServices categoryServices;

	/**
	 * Returns list of categories based on the filter.
	 * 
	 * @param specificFor Customer specific filter containing either "Male" or "Female".
	 * @return			  ResponesEntity with saved list of categories and a ResponseStatus of OK.
	 */
	@GetMapping("/getCategories/{catFilter}")
	public ResponseEntity<List<CategoryDto> > getCategories(@PathVariable("catFilter") String specificFor) {
		try {
			List<CategoryDto>  results = categoryServices.getCategoriess(specificFor);
			return new ResponseEntity<>(results,HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
	}

}
