package com.woodland.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="addressObj")
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long address_id; 
	private String address_line; 
	private int pincode; 
	private String City; 
	private String State; 
	private String landmark; 
	private String type;
	
	@ManyToOne
	@JoinColumn(name="customer_id")
	private Customer custAddress;
	
	
	@OneToMany(mappedBy="orderAddress",
			cascade=CascadeType.ALL)
	private List<Orders> orders;


}
