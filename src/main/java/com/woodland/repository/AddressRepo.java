package com.woodland.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.woodland.entity.Address;


@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
	
	@Query(value="select * from address where customer_id= :customer_id",nativeQuery=true)
	List<Address> getAddressByCustomerId(@Param("customer_id") Long customerId);
	
	@Query(value="select * from address where address_id=:address_id",nativeQuery=true)
	Address getAddressByAddressId(@Param("address_id") Long addressId);
	

}
