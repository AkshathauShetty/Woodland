package com.woodland.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woodland.dto.PaymentDto;
import com.woodland.exception.InputInvalid;
import com.woodland.service.WoodlandPaymentServices;



@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerPaymentController {
	
	@Autowired
	WoodlandPaymentServices paymentServices;
	

    /** 
	 * save the payment
	 * @return List<OrderHistoryDto>
	 */
    @PostMapping("/savePayment")
    public ResponseEntity<PaymentDto> savePayment(@RequestBody PaymentDto payment ,Principal principal){  
    	
    	try {
    		PaymentDto result = paymentServices.savePayment(payment,principal);
    		return new ResponseEntity<>(result, HttpStatus.OK);
    	}
    	catch(Exception e) {
    		throw e;
    	}
    }
}
