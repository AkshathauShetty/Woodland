package com.woodland.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="ordersGet")
@Entity
public class Orders{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long orderId;
	private Double orderTotalAmount;
	private Integer orderTotalItems;
	private Double orderDonationAmount;
	private Integer orderDeliveryAmount;
	private Date orderDate;
	private String orderStatus;
	

	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer customerOrder;

	@OneToMany(mappedBy = "orders")
	List<AddedOrder> p_orders;
	
	@ManyToOne
	@JoinColumn(name="order_address_id")
	private Address orderAddress;
	
	@OneToOne(mappedBy="orders",
			cascade=CascadeType.ALL)
	private Payment paymentId;

}
	