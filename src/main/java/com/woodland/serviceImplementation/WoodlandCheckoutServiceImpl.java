package com.woodland.serviceImplementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woodland.dto.CartDto;
import com.woodland.dto.CartTotal;
import com.woodland.dto.OrderDto;
import com.woodland.dto.OrderHistoryDto;
import com.woodland.dto.PaymentDto;
import com.woodland.entity.AddedCart;
import com.woodland.entity.AddedOrder;
import com.woodland.entity.Address;
import com.woodland.entity.Cart;
import com.woodland.entity.Customer;
import com.woodland.entity.Orders;
import com.woodland.entity.Payment;
import com.woodland.entity.ProductPicSizes;
import com.woodland.entity.ProductPictures;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.relationEntity.AddedCartKey;
import com.woodland.relationEntity.AddedOrderKey;
import com.woodland.repository.AddedCartRepo;
import com.woodland.repository.AddedOrderRepo;
import com.woodland.repository.AddressRepo;
import com.woodland.repository.CartRepo;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.OrderRepo;
import com.woodland.repository.PaymentRepo;
import com.woodland.repository.ProductImageRepo;
import com.woodland.repository.ProductPicSizesRepo;
import com.woodland.repository.ProductsRepo;
import com.woodland.service.WodlandCheckoutServices;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;

@Service
public class WoodlandCheckoutServiceImpl implements WodlandCheckoutServices{

	
	@Autowired
	private AddedOrderRepo addedOrderRepo;
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private ProductsRepo productsRepo;
	@Autowired
	private ProductImageRepo productImageRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private ProductPicSizesRepo picSizesRepo;
	@Autowired
	private PaymentRepo paymentRepo;
	
	@Autowired
	private AddressRepo addressRepo;
	
	
	/** 
	 *  saves the order in the repository
	 * @param order Dto
	 * @return OrderDto
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
					List <CartDto> cdtos = new ArrayList<>();
					for(CartDto cdto : cartdtoList) {
						ProductPictures productPic = productImageRepo.findById(cdto.getProdPicId().getProductPicId()).orElse(null);
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
						throw new DataNotFound("FAiled to save orders");
					}
					
				}
				else {
					throw new DataNotFound("Address not found!");
				}
			}
			else {
				throw new DataNotFound("Customer not FOund!");
			}
		}
		else {
			throw new InputInvalid("Invalid Input details");
		}
	
	}


	/** 
	 *  returns the list of all order details from the repository
	 * @return List<OrderHistoryDto>
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
	 * Converts the tuple details from the query  to the Orderhistory dto
	 * @param orderDetailList
	 * @return
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
	 * return the previously ordered items 
	 * @return List<OrderHistoryDto>
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

	/**
	 * saves  the payment
	 * @param payment 
	 */
	@Override
	public PaymentDto savePayment(PaymentDto payment, Principal principal) {
		if(payment!=null && payment.getOrderid()!=null && principal!=null) {
			Orders order = orderRepo.findOrdersByOrderId(payment.getOrderid());
			Payment paymentNew = Payment.paymentobj(0l, order.getOrderTotalAmount(), order.getOrderDate(), payment.getPaymentStatus(), order);		
			Payment paymentSaved =paymentRepo.save(paymentNew);
			PaymentDto paymentDto = PaymentDto.paymentobj(paymentSaved.getPaymentId(), paymentSaved.getPaymentAmount(), paymentSaved.getPaymentDate(), paymentSaved.getPaymentStatus() ,null,null);
			return paymentDto;
		}
		else {
			throw new InputInvalid("Invalid inputs");
		}
	}

}
