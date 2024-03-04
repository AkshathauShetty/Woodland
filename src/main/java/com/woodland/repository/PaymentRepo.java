package com.woodland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woodland.entity.Payment;


@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
	
	

}
