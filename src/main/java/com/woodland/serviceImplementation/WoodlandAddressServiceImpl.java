package com.woodland.serviceImplementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woodland.dto.AddressDto;
import com.woodland.entity.Address;
import com.woodland.entity.Customer;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.mapper.AddressMapper;
import com.woodland.repository.AddressRepo;
import com.woodland.repository.CustomerRepo;
import com.woodland.service.WoodlandAddressServices;

@Service
public class WoodlandAddressServiceImpl implements WoodlandAddressServices{
	
	@Autowired
	private CustomerRepo customerRepo;	
	@Autowired 
	private AddressRepo addressRepo;
	@Autowired
	private AddressMapper addressMapper;
	
	/**
	 * Add the address to the list of addresses of the customer. 
	 * 
	 * @param principal		authentication header object.
	 * @param addressDto	
	 * @return				saved addressDto object.
	 */
	@Override
	public AddressDto saveAddress(Principal principal ,AddressDto addressDto) {
		if(principal!=null && principal.getName()!=null) {
			addressDto.setCustomerPhone(principal.getName());
			Customer customer = customerRepo.findByPhone(addressDto.getCustomerPhone());
			if(customer!=null) {
				Address address = addressRepo.save(Address.addressObj(0l, addressDto.getAddressLine(), addressDto.getPincode(), addressDto.getCity(), addressDto.getState(), addressDto.getLandmark(), addressDto.getType(), customer,null));
				addressDto.setAddressId(address.getAddress_id());
				return addressDto;
			}
			else {
				throw new DataNotFound("User not found! Login again");
			}
		}
		else {
			throw new InputInvalid("Invalid input");
		}
	}

	
	/**
	 * Returns the list of addresses of the customer. 
	 * 
	 * @param principal authentication header object.
	 * @return			List of addresses of the customer
	 */
	@Override
	public List<AddressDto> getAddress(Principal principal) {
		if(principal!=null  && principal.getName()!=null) {
			String phone = principal.getName();
			Customer customer= customerRepo.findByPhone(phone);
			if(customer!=null) {
				Long customerId = customer.getCustomer_id();
				List<Address> addresses = addressRepo.getAddressByCustomerId(customerId);
				List<AddressDto> addressdtos = new ArrayList<>();
				addresses.forEach((address)->{
					AddressDto addressDto = addressMapper.addressDtoTOAddress(address);
					addressdtos.add(addressDto);
				});
				return addressdtos;
			}
			else {
				throw new DataNotFound("No customer found.");
			}
			
		}
		else {
			throw new InputInvalid("Invalid credentials");
		}
	}

	/**
	 * Returns the edited address.
	 * Modify the saved address using the phone number of the user form the principal object. 
	 * 
	 * @param principal	 authentication header object.
	 * @param addressDto AddressDto to which the saved address is to be modified into.  
	 * @return			 Saved addressDto.
	 */
	@Override
	public AddressDto editAddress(Principal principal, AddressDto addressDto) {
		if(addressDto!=null) {
			Address address = addressRepo.getAddressByAddressId(addressDto.getAddressId());
			address.setAddress_line(addressDto.getAddressLine());
			address.setCity(addressDto.getCity());
			address.setLandmark(address.getLandmark());
			address.setPincode(addressDto.getPincode());
			address.setState(addressDto.getState());
			address.setType(addressDto.getType());
			addressRepo.save(address);
			return addressDto; 			
		}
		else {
			throw new InputInvalid("Invalid input");
		}

	}

	/**
	 * Returns "Address deleted successfully" on deleting the address.
	 * 
	 * @param principal	 authentication header object.
	 * @param addressId	 Id of the address to be deleted.
	 * @return			 "Address deleted successfully" on deleting the address.
	 */
	@Override
	public String deleteAddress(Principal principal, Long addressId) {
		
		if(addressId!=null) {
			Address address = addressRepo.getAddressByAddressId(addressId);	
			if(address!=null) {
				addressRepo.delete(address);
				return "Address deleted successfully";
			}
			else {
				throw new DataNotFound("Address not found");
			}
		}
		else {
			throw new InputInvalid("Invalid input");
		}

	}
}
