package com.woodland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woodland.entity.AddedCart;
import com.woodland.entity.Cart;
import com.woodland.entity.Products;


@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

	@Query(value="select * from cart where customer_id = :cust_id",nativeQuery=true)
	Optional<Cart> findByCustomerCarts(@Param("cust_id") Long customerCartid);
	
	
	
	@Query(value="select * from cart where customer_id = :cust_id",nativeQuery=true)
	Optional<Cart> findByCustomerId(@Param("cust_id") Long customerid);
	
	@Query(value="select cart_id from cart where customer_id = :cust_id",nativeQuery=true)
	Long getCartIdByCustomerID(@Param("cust_id") Long customerid);
	

	
}
