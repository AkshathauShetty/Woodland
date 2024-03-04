package com.woodland.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woodland.dto.OrderDto;
import com.woodland.dto.OrderHistoryDto;
import com.woodland.service.WodlandOrdersServices;

@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerOrdersController {
	
	@Autowired
	WodlandOrdersServices checkoutServices;
	

	/** 
	 * Returns the saved orders. 
	 * Saves the Order entity along with the addedOrder entity for the customer.
	 * 
	 * @param principal	authentication header object.
	 * @param orderDto	Orders entity of the customer.
	 * @return 			ResponseEntity with the saved OrderDto for the customer along with the HttpStatus of OK.
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
	 * Returns the list order details ordered 7days back.
	 * 
	 * @param principal	authentication header object.
	 * @return			ResponseEntity with the List of OrderHistoryDto with all the orders placed by the customer along with the HttpStatus of OK.
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
	 * Return the ordered items within 7days.
	 * 
	 * @param principal authentication header object.
	 * @return			ResponseENtity with the list of OrdereHistoryDto and a HttpStatus of OK.
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
