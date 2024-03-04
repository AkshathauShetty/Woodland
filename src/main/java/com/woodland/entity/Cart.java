package com.woodland.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="cartGet")
@Entity
public class Cart{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long cartId;
	private Double cartTotalAmount;
	private Integer cartTotalItems;
	
	@OneToOne
	@JoinColumn(name="customer_id")
	private Customer customerCart;
	
	 @OneToMany(mappedBy = "cart")
	 Set<AddedCart> p_carts;
}