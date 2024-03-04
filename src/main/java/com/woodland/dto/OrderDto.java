package com.woodland.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="orderDto")
public class OrderDto {

	private Long orderId;
	private Double orderTotalAmount;
	private Integer orderTotalItems;
	private Double orderDonationAmount;
	private Integer orderDeliveryAmount;
	
	private String orderCustomerPhone;
	private List<CartDto> cartDto;	
	private Long addressId;
	private Date orderDate;
	private String orderStatus;

}

