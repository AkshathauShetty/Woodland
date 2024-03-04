package com.woodland.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woodland.dto.CartDto;
import com.woodland.dto.CartTotal;
import com.woodland.dto.CustomerDto;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.service.WoodlandCartServices;



@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerCartControlller {


	@Autowired
	private WoodlandCartServices woodlandCartservices;

	/** 
	 * calls a method to save the cart item to the user's cart list 
	 * @param {CartDto} cartdto - user cartdto to be saved
	 * @return { ResponseEntity<CartDto> } - A response entity containing the saved cartdto, status code
	 */
    @PostMapping("/addtoCart")
    public ResponseEntity<CartDto> addtoCarts(Principal principal, @RequestBody CartDto cartdto){ 
    	try {
    		CartDto cartDtoresult  = woodlandCartservices.addTocart(principal,cartdto);
        	return new ResponseEntity<>(cartDtoresult, HttpStatus.OK);   
    	}
    	catch(Exception e) {
    		throw e;
    	}
    		 	

    }
    
    /** 
	 *  Returns the cart details of the user using user cartId
	 * @param {Long} cartId - cartid of the user
	 * @return  {ResponseEntity<List<CartTotal>>} -  A response entity containing the list of cart items associated with the cartId,  HTTP status code 
	 */
    @GetMapping("/displayCart/{cartId}")
    public ResponseEntity<List<CartTotal>> displayCart(@PathVariable("cartId") Long cartId){  
    	try {
    		List<CartTotal>  cartTotals=woodlandCartservices.getCart(cartId);
        	return new ResponseEntity<>(cartTotals, HttpStatus.OK);  
    	}
    	catch(Exception e) {
    		throw e;
    	}
    }
    
    /**
	 * returns the cart id associated with the user
	 * @return {ResponseEntity<Long>} -    A response entity containing cart id ,  Http status code.
	 */
	@GetMapping("/getCartId")
	public ResponseEntity<Long> getCartId(Principal principal) {	
		try {
			Long cartId = woodlandCartservices.getCartId(principal);
			return new ResponseEntity<>(cartId,HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	/**
	 * delete the product from the cart based on the primary key details provided in the cartDto
	 * @param {CartDto} cartdto
	 * @return {ResponseEntity<String>} 
	 */
	@DeleteMapping("/deletecart")
	public ResponseEntity<String> deleteCart(@RequestBody CartDto cartdto){
		try {
			String response = woodlandCartservices.deleteCart(cartdto);
			return new ResponseEntity<>(response,HttpStatus.OK);	
		}
		catch(Exception e){
			throw e;
		}	

	}
	
	/** 
	 * Updates the cart item quantity based on the keys: cartId and ProductPicturesSizeId
	 * @param {List<CartDto>} cart - List of CartDto 
	 * @return {  ResponseEntity<List<CartDto>> } - response entity containning the List of CartDto , response status.
	 */
	@PutMapping("/updateCart")
	public ResponseEntity<String> updateCart(@RequestBody List<CartDto> cartdto){
		try {
			String response =woodlandCartservices.updateCart(cartdto);
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
			
	}
	
	/**
	 * delete the  all the products from the added_cart after order is placed
	 * @param {CartDto} cartdto
	 * @return {ResponseEntity<String>} 
	 */
	@DeleteMapping("/deleteAddedcart")
	public ResponseEntity<String> deleteAddedCart(Principal  principal){
		try {
			String response = woodlandCartservices.deleteAddedCart(principal);
			return new ResponseEntity<>(response,HttpStatus.OK);	
		}
		catch(Exception e) {
			throw e;
		}
	}
}
