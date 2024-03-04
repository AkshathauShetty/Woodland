package com.woodland.dto;

import java.util.Date;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor(staticName="addressDto")
public class AddressDto {

	private String customerPhone;
	private Long addressId; 
	private String addressLine; 
	private int pincode; 
	private String city; 
	private String state; 
	private String landmark; 
	private String type;

}
