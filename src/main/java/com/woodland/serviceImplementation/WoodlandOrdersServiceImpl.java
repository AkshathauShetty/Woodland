package com.woodland.serviceImplementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woodland.dto.CartDto;
import com.woodland.dto.OrderDto;
import com.woodland.dto.OrderHistoryDto;
import com.woodland.entity.AddedOrder;
import com.woodland.entity.Address;
import com.woodland.entity.Customer;
import com.woodland.entity.Orders;
import com.woodland.entity.ProductPicSizes;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.relationEntity.AddedOrderKey;
import com.woodland.repository.AddedOrderRepo;
import com.woodland.repository.AddressRepo;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.OrderRepo;
import com.woodland.repository.ProductPicSizesRepo;
import com.woodland.service.WodlandOrdersServices;

import jakarta.persistence.Tuple;

@Service
public class WoodlandOrdersServiceImpl implements WodlandOrdersServices{

	
	@Autowired
	private AddedOrderRepo addedOrderRepo;
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private ProductPicSizesRepo picSizesRepo;
	@Autowired
	private AddressRepo addressRepo;
	
	
	/** 
	 * Returns the saved orders. 
	 * Saves the Order entity along with the addedOrder entity for the customer.
	 * 
	 * @param principal	authentication header object.
	 * @param orderDto	Orders entity of the customer.
	 * @return 			Saved OrderDto for the customer.
	 */
	@Override
	public OrderDto saveOrder(Principal principal,OrderDto orderDto) {
		if(orderDto!=null && principal!=null && principal.getName()!=null) {
			orderDto.setOrderCustomerPhone(principal.getName());
			List<CartDto> cartdtoList = orderDto.getCartDto();
			Customer customers = customerRepo.findByPhone(orderDto.getOrderCustomerPhone());
			if(customers!=null) {
				Address address = addressRepo.findById(orderDto.getAddressId()).orElseGet(null);
				if(address!=null) {
					Orders order = Orders.ordersGet(0l, orderDto.getOrderTotalAmount(), orderDto.getOrderTotalItems(), orderDto.getOrderDonationAmount(), orderDto.getOrderDeliveryAmount(),orderDto.getOrderDate(),orderDto.getOrderStatus(),customers, null,address,null);	
					Orders newOrder = orderRepo.save(order);
					for(CartDto cdto : cartdtoList) {
						ProductPicSizes picSizes = picSizesRepo.findById(cdto.getProdPicSizeId().getProductPicSizeId()).orElse(null);
						AddedOrderKey addedcartKey =AddedOrderKey.addedorderkeyGet(order.getOrderId(), picSizes.getProductPicSizeId());
						AddedOrder addedOrder = AddedOrder.addedorderGet(addedcartKey, order,picSizes,cdto.getProdQuantity());
						addedOrderRepo.save(addedOrder);	
					}
					if(newOrder!=null) {
						orderDto.setOrderId(newOrder.getOrderId());
						return orderDto;
					}
					else {
						throw new DataNotFound("Failed to save orders");
					}
					
				}
				else {
					throw new DataNotFound("Address not found!");
				}
			}
			else {
				throw new DataNotFound("Customer not Found!");
			}
		}
		else {
			throw new InputInvalid("Invalid Input details");
		}
	
	}


	/** 
	 * Returns the list order details ordered 7days back.
	 * 
	 * @param principal	authentication header object.
	 * @return			List of OrderHistoryDto with all the orders placed by the customer.
	 */
	@Override
	public List<OrderHistoryDto> getOrderHistory(Principal principal) {
		if(principal!=null && principal.getName()!=null) {
			String phone = principal.getName();
			List <OrderHistoryDto> orderHistory = new ArrayList<>();
			Customer customer = customerRepo.findByPhone(phone);
			if(customer!=null) {
				List<Orders> orderList =  orderRepo.findOrdersByCusstomerId(customer.getCustomer_id()); 
				orderList.forEach((order)->{
					List<Tuple> orderDetailList = orderRepo.getorderDetails(order.getOrderId());
					if(orderDetailList!=null) {
						 List<OrderHistoryDto> orderHistoryList = ordertoOrderHistory(orderDetailList);
						 orderHistory.addAll(orderHistoryList);
					}
				});
				return orderHistory;
			}
			else {
				throw new DataNotFound("No customers found");
			}
		}
		
		return null;
	}

	/**
	 * Converts the order details from the query  to the OrderhistoryDto
	 * 
	 * @param orderDetailList list of orders details from the JPA query.
	 * @return				  converted list of orderHistoryDto
	 */
	public List<OrderHistoryDto> ordertoOrderHistory(List<Tuple> orderDetailList){
		if(orderDetailList!=null) {
			List<OrderHistoryDto> orderHistoryDto =
					orderDetailList.stream()
					.map(item->
					OrderHistoryDto.creatreOrderHistoryDto(item.get(11,Long.class),
							item.get(0,Long.class),
							item.get(1,String.class),
							item.get(2,String.class), 
							item.get(3,String.class),
							item.get(4,Integer.class),
							item.get(5,Integer.class),
							item.get(6,Double.class),
							item.get(7,Integer.class),
							item.get(8,String.class),
							item.get(9,Long.class),
							0d,
							item.get(10,Date.class),
							null
							))
					.collect(Collectors.toList());
			return orderHistoryDto;
		}
		else {
			return null;
		}
		
	}


	/**
	 * Return the ordered items within 7days.
	 * 
	 * @param principal authentication header object.
	 * @return			List of OrdereHistoryDto
	 */
	@Override
	public List<OrderHistoryDto> getRecentOrderHistory(Principal principal) {
		if(principal!=null && principal.getName()!=null) {
			String phone = principal.getName();
			List <OrderHistoryDto> orderHistory = new ArrayList<>();
			Customer customer = customerRepo.findByPhone(phone);
			if(customer!=null) {
				List<Orders> orderList =  orderRepo.findOrdersByCusstomerId(customer.getCustomer_id()); 
				orderList.forEach((order)->{
					List<Tuple> orderDetailList = orderRepo.getRecentorderDetails(order.getOrderId());
					if(orderDetailList!=null) {
						 List<OrderHistoryDto> orderHistoryList = ordertoOrderHistory(orderDetailList);
						 orderHistory.addAll(orderHistoryList);
					}
				});
				return orderHistory;
			}
			else {
				throw new DataNotFound("No customers found");
			}
		}
		else {
			throw new InputInvalid("Inalid inputs");
		}
	}


}
