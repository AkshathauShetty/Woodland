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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="customerobj")
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long customer_id; 
	private String firstname; 
	private String lastname; 
	private String email; 
	private String phone; 
	private Date dob; 
	private String gender; 
	private String password; 
	

	
	@OneToOne(mappedBy="customerCart",
				cascade=CascadeType.ALL)
	private Cart cartId;
	
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy="custAddress")
	private List<Address> customerAddress;
	

	
	@OneToMany(cascade = CascadeType.ALL,
			mappedBy="customerOrder")
	private List<Orders> customerOrderList;
	
}
