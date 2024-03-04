package com.woodland.service;

import java.security.Principal;
import java.util.List;

import com.woodland.dto.OrderDto;
import com.woodland.dto.OrderHistoryDto;

public interface WodlandOrdersServices {
	
	public OrderDto saveOrder(Principal principal,OrderDto orderDto);
	public List<OrderHistoryDto> getOrderHistory(Principal principal);
	public List<OrderHistoryDto> getRecentOrderHistory(Principal principal);


}
