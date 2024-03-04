package com.woodland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woodland.entity.Category;
import com.woodland.service.WoodlandAdminServices;

@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private WoodlandAdminServices woodlandServices;

	/**
	 * Creates a new Category.
	 * Save the category along with its sub category and sub category types.
	 * 
	 * @param category Category object containing the sub category and sub category types to be saved.
	 * @return		   HttpResponse entity with Saved category object and the response status of OK.
	 */
	@PostMapping("/saveCategories")	
	public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
		try {
			return new ResponseEntity<>(woodlandServices.saveCategory(category),HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
		
	}
}
