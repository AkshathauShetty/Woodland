package com.woodland.serviceImplementation;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woodland.dto.PaymentDto;
import com.woodland.entity.Orders;
import com.woodland.entity.Payment;
import com.woodland.exception.InputInvalid;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.OrderRepo;
import com.woodland.repository.PaymentRepo;
import com.woodland.repository.ProductImageRepo;
import com.woodland.repository.ProductPicSizesRepo;
import com.woodland.repository.ProductsRepo;
import com.woodland.service.WoodlandPaymentServices;


@Service
public class WoodlandPaymentServiceImpl implements WoodlandPaymentServices{
	
	@Autowired
	private OrderRepo orderRepo;
	@Autowired
	private PaymentRepo paymentRepo;
	
	
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
