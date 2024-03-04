package com.woodland.service;

import java.security.Principal;
import java.util.List;

import com.woodland.dto.AddressDto;

public interface WoodlandAddressServices {
	
	
	public AddressDto saveAddress(Principal principal,AddressDto addressDto);
	public List<AddressDto> getAddress(Principal principal);
	public AddressDto editAddress(Principal principal,AddressDto addressDto);
	public String deleteAddress(Principal principal,Long addressId);

}
