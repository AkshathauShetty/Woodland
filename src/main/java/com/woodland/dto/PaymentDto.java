package com.woodland.dto;

import java.util.Date;

import com.woodland.entity.Orders;
import com.woodland.entity.Payment;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="paymentobj")
public class PaymentDto {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long paymentId;
	private Double paymentAmount;
	private Date paymentDate;
	private String paymentStatus;
	private OrderDto Orders;
	private Long orderid;

}

	
