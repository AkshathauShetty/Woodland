package com.woodland.serviceImplementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.woodland.dto.AddressDto;
import com.woodland.entity.Address;
import com.woodland.entity.Customer;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.mapper.AddressMapper;
import com.woodland.repository.AddressRepo;
import com.woodland.repository.CategoryRepo;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.ProductImageRepo;
import com.woodland.repository.ProductsRepo;
import com.woodland.repository.SubCategoryRepo;
import com.woodland.repository.SubcatTypesRepo;
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
	 * return the token for the registered user
	 * @param {AddressDto} customerDto - AddressDto containing the address details to be saved.
	 * @return  { AddressDto } - saved address.
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
	 * Returns the addresses list containingf the given user.
	 * @return  { List<AddressDto>} - List of addresses
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
	 * @param {AddressDto} addressDto - Men / women specific products
	 * @return  {AddressDto} - List of addresses
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
	 * Returns the deleted address.
	 * @param{long} addressId 
	 * @return {AddressDto} addressDto - addressDto returned 
	 */
	@Override
	public String deleteAddress(Principal principal, Long addressId) {
		
		if(addressId!=null) {
			Address address = addressRepo.getAddressByAddressId(addressId);	
			if(address!=null) {
				addressRepo.delete(address);
				return "Address delted successfully";
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
