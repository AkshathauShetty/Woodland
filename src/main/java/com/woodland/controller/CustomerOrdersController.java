package com.woodland.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.woodland.dto.OrderDto;
import com.woodland.dto.OrderHistoryDto;
import com.woodland.dto.PaymentDto;
import com.woodland.exception.InputInvalid;
import com.woodland.service.WodlandCheckoutServices;

@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerOrdersController {
	
	@Autowired
	WodlandCheckoutServices checkoutServices;
	

	/** 
	 * saves the order in the repository
	 * @param order Dto
	 * @return OrderDto
	 */
    @PostMapping("/saveOrder")
    public ResponseEntity<OrderDto> saveOrder(Principal principal,@RequestBody OrderDto orderDto){
    	try {
    		OrderDto orderdto=checkoutServices.saveOrder(principal,orderDto);
    		return new ResponseEntity<>(orderdto, HttpStatus.OK);   
    	}
    	catch(Exception e) {
    		throw e;
    	}	
    }
    
    /** 
	 * gets the previous orders from the database
	 * @return List<OrderHistoryDto>
	 */
    @GetMapping("/getPrevOrders")
    public ResponseEntity<List<OrderHistoryDto>> getAllOrders(Principal principal){   
    	try {
    		List<OrderHistoryDto> result = checkoutServices.getOrderHistory(principal);
    		return new ResponseEntity<>(result, HttpStatus.OK);
    	}
    	catch(Exception e) {
    		throw e;
    	}

    }
    
    /** 
	 * gets the recent orders from the database
	 * @return List<OrderHistoryDto>
	 */
    @GetMapping("/getRecentOrders")
    public ResponseEntity<List<OrderHistoryDto>> getRecentOrders(Principal principal){   
		try {
			List<OrderHistoryDto> result = checkoutServices.getRecentOrderHistory(principal);
    		return new ResponseEntity<>(result, HttpStatus.OK);
		}
		catch(Exception e) {
			throw e;
		}
    }
}
