package com.woodland.mapper;

import org.springframework.stereotype.Component;

import com.woodland.dto.AddressDto;
import com.woodland.entity.Address;


@Component
public class AddressMapper {

	public Address addressToDto(AddressDto addressDto) {
		Address address = Address.addressObj(0l,
				addressDto.getAddressLine(), 
				addressDto.getPincode(), 
				addressDto.getCity(), 
				addressDto.getState(), 
				addressDto.getLandmark(), 
				addressDto.getType(), 
				null, 
				null);
		return address;
	}
	
	public AddressDto addressDtoTOAddress(Address address) {
		AddressDto addressDto = AddressDto.addressDto(null, 
				address.getAddress_id(), 
				address.getAddress_line(),
				address.getPincode(), 
				address.getCity(),
				address.getState(),
				address.getLandmark(), 
				address.getType());
		return addressDto;
	}
}
