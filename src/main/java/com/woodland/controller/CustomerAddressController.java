package com.woodland.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woodland.dto.AddressDto;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.service.WoodlandAddressServices;

@RestController
@RequestMapping("/woodland")
@CrossOrigin(origins = "*")
public class CustomerAddressController {
	
	@Autowired
	WoodlandAddressServices addressServices; 

	
	/**
	 * return all the address of the customer
	 * @param {AddressDto} customerDto - AddressDto containing the address details to be saved.
	 * @return  { ResponseEntity<AddressDto> } - saved address.
	 */
	@GetMapping("/getAddress")
	public ResponseEntity<List<AddressDto>> getAddress(Principal principal) {
			try {
			List<AddressDto> addressDtoResult=addressServices.getAddress(principal);
			return new ResponseEntity<>(addressDtoResult,HttpStatus.OK);
			}
			catch(DataNotFound e){
				throw new DataNotFound(e.getMessage(),e);
			}
			catch(InputInvalid e) {
				throw new InputInvalid(e.getMessage(),e);
			}
			catch(Exception e) {
				throw e;
			}
		
	}
	
	/**
	 * save the customer address
	 * @param {AddressDto} customerDto - AddressDto containing the address details to be saved.
	 * @return  { ResponseEntity<AddressDto> } - saved address.
	 */
	@PostMapping("/saveAddress")
	public ResponseEntity<AddressDto> saveAddress(Principal principal,@RequestBody AddressDto addressDto) {
			try {
				AddressDto addressDtoResult=addressServices.saveAddress(principal,addressDto);
				return new ResponseEntity<>(addressDtoResult,HttpStatus.OK);
			}
			catch(DataNotFound e){
				throw new DataNotFound(e.getMessage(),e);
			}
			catch(InputInvalid e) {
				throw new InputInvalid(e.getMessage(),e);
			}
			catch(Exception e) {
				throw e;
			}

		
	}
	
	/**
	 * edit the address of the customer
	 * @param {AddressDto} customerDto - AddressDto containing the address details to be saved.
	 * @return  { ResponseEntity<AddressDto> } - saved address.
	 */
	@PostMapping("/editAddress")
	public ResponseEntity<AddressDto> editAddress(Principal principal,@RequestBody AddressDto addressDto) {	
			try {
				AddressDto addressDtoResult=addressServices.editAddress(principal,addressDto);
				return new ResponseEntity<>(addressDtoResult,HttpStatus.OK);
			}
			catch(DataNotFound e){
				throw new DataNotFound(e.getMessage(),e);
			}
			catch(InputInvalid e) {
				throw new InputInvalid(e.getMessage(),e);
			}
			catch(Exception e) {
				throw e;
			}
			
	}
	
	/**
	 * delete the address
	 * @param {AddressDto} customerDto - AddressDto containing the address details to be saved.
	 * @return  { ResponseEntity<AddressDto> } - saved address.
	 */
	@GetMapping("/delAddress/{addressId}")
	public ResponseEntity<String> deleteAddress(Principal principal,@PathVariable("addressId")Long addressId) {
			try {
				String result=addressServices.deleteAddress(principal,addressId);
				return new ResponseEntity<>(result,HttpStatus.OK);
			}
			catch(DataNotFound e){
				throw new DataNotFound(e.getMessage(),e);
			}
			catch(InputInvalid e) {
				throw new InputInvalid(e.getMessage(),e);
			}
			catch(Exception e) {
				throw e;
			}
			
		}

	}
