package com.woodland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woodland.dto.CustomerDto;
import com.woodland.security.JwtService;

@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class WoodlandJwtController {
	
    @Autowired
    private AuthenticationManager authenticationManager; 
    @Autowired
    private JwtService jwtService;

	
	/**
	 * return the token for the registered user
	 * @param {CustomerDto} customerDto - customer dto object  containing the values to be updated. 
	 * @return  {String} - Token returned 
	 */
	@PostMapping("/generateTokens") 
    public String authenticateAndGetToken(@RequestBody CustomerDto authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getPhone(), authRequest.getPassword()));     
        if (authentication.isAuthenticated()) { 
            return jwtService.generateToken(authRequest.getPhone()); 
        } else { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    } 
	
}
