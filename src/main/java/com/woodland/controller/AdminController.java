package com.woodland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woodland.entity.Category;
import com.woodland.security.JwtService;
import com.woodland.service.WoodlandAdminServices;
import com.woodland.service.WoodlandServices;

@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	private WoodlandAdminServices woodlandServices;

	
	@PostMapping("/saveCategories")	
	public ResponseEntity<?> saveCategory(@RequestBody Category category) {
		try {
			return new ResponseEntity<>(woodlandServices.saveCategory(category),HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
		
	}
}
