package com.woodland.service;

import java.security.Principal;
import java.util.List;

import com.woodland.dto.OrderDto;
import com.woodland.dto.OrderHistoryDto;
import com.woodland.dto.PaymentDto;

public interface WodlandCheckoutServices {
	
	public OrderDto saveOrder(Principal principal,OrderDto orderDto);
	public List<OrderHistoryDto> getOrderHistory(Principal principal);
	public List<OrderHistoryDto> getRecentOrderHistory(Principal principal);
	public PaymentDto savePayment(PaymentDto payment,Principal principal);
	

}
