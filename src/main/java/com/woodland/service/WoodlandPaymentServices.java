package com.woodland.service;

import java.security.Principal;

import com.woodland.dto.PaymentDto;

public interface WoodlandPaymentServices {
	
	public PaymentDto savePayment(PaymentDto payment,Principal principal);

}
