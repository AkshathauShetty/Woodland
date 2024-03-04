package com.woodland.serviceImplementation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.woodland.dto.CustomerDto;
import com.woodland.dto.ProductPicSizesDto;
import com.woodland.dto.ProductPicturesdto;
import com.woodland.dto.ProductsDto;
import com.woodland.entity.Customer;
import com.woodland.entity.ProductPicSizes;
import com.woodland.entity.ProductPictures;
import com.woodland.entity.Products;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.ProductsRepo;
import com.woodland.service.WoodlandCustomerServices;


@Service
@Primary
public class WoodlandCustomerServiceImpl implements WoodlandCustomerServices,UserDetailsService {

	
	
	@Autowired
	private ProductsRepo productsRepo;
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	/**
	 * Calculates the final price based by applying the offer price.
	 * 
	 * @param productdtoPrice
	 * @param productdtoOffers
	 * @return					Return the final price computed by applying the offer over the original price.
	 */
	public Double calculateFinalprice(Double productdtoPrice, Integer productdtoOffers) {
		return productdtoPrice-(productdtoPrice*productdtoOffers/100);
	}
	
	/**
	 * Converts the list of products to list of product dto's. 
	 * 
	 * @param  listOfProducts list of products
	 * @return 				  list of product dto's.
	 */
	public List<ProductsDto> productsTodto(List<Products> listOfProducts){
		List<ProductsDto> productDto= new ArrayList<>();	
		if(listOfProducts!=null) {
			for(Products product :listOfProducts) {
				// for each of the product create an object of product DTO
				ProductsDto pdto = ProductsDto.create(product.getProductId(), product.getProductPrice(), product.getSuitableFor(), product.getProductOffers(), null,product.getBestSeller(),null,null,null);
				// final price is calculated based on the values of price and offer on the price
				pdto.setProductFinalPrice(calculateFinalprice(pdto.getProductdtoPrice(), pdto.getProductdtoOffers()));
				List<ProductPictures> productPictures = product.getProductPictures();
				List<ProductPicturesdto> productPicturedtolist = new ArrayList<>();
				for(ProductPictures pictures : productPictures) {				
					//ProductPicturesdto productPicturedto = ProductPicturesdto.getPdto(pictures.getProductPicId(), pictures.getProductColor(), pictures.getProductPicUrl());
					// for each picture of the products , create a pictureDto object
					ProductPicturesdto productPicturedto = ProductPicturesdto.createProductPic(pictures.getProductPicId(), pictures.getProductColor(), pictures.getProductPicUrl(),pictures.getProductPicName(),null,null);
					// native query is used to get the list of all the sizes available for the picture
					List<String> prod_sizes = productsRepo.getAllSizes(pictures.getProductPicId());
					// set the list of sizes available in the pictureDto
					productPicturedto.setProductSizes(prod_sizes);
					// for each picture get the sizes of the picttures
					List<ProductPicSizes> productPicSizes = pictures.getProductPictureSizes();
					List<ProductPicSizesDto> productPicSizesDto = new ArrayList<>();
					for(ProductPicSizes picSizes:productPicSizes) {
						// create a dto
						ProductPicSizesDto picsizeDto = ProductPicSizesDto.getPdPicdto(picSizes.getProductPicSizeId(), picSizes.getProductPicSize(),picSizes.getProductPicSizeCount());
						/// save each size dto in the list of size dtos's
						productPicSizesDto.add(picsizeDto);
					}
					// set the picture dto's size mapping to the list of size dto
					productPicturedto.setProductPicSizesdto(productPicSizesDto);
					// add the generated picture dto to the list of pictureDto
					productPicturedtolist.add(productPicturedto);
					
				}
				// set the picture dto mapping of the product to list of picture dto
				pdto.setProductPicturesdto(productPicturedtolist);
				// add the generated product dto to the list of product dto
				productDto.add(pdto);
			}	
			System.out.println("lp done"+productDto);
			if(productDto!=null && !productDto.isEmpty()) {
				return productDto;	
			}
			else {
				throw new DataNotFound("Error occured during converesion");
			}	
		}
		else {
			throw new DataNotFound("No matching results found for the query");
		}
		
	}
	
	/**
	 * Register the customer. 
	 * 
	 * @param	customerDto	CustomerDto object that needs to be registered
	 * @return				Returns the registered customerDto object. Used for generating the token.
	 */
	@Override
	public CustomerDto registerCustomer(CustomerDto customerDto) {
		if(customerDto!=null && customerDto.getPhone()!=null && customerDto.getPassword()!=null) {
			Customer customer= customerRepo.findByPhone(customerDto.getPhone());
			if(customer==null) {
				String encryptedPassword = passwordEncoder.encode(customerDto.getPassword());
				Customer cobj = Customer.customerobj(0l, customerDto.getFirstname(), customerDto.getLastname(), customerDto.getEmail(), customerDto.getPhone(), customerDto.getDob(), customerDto.getGender(), encryptedPassword,null,null,null);	
				customerRepo.save(cobj);
				return customerDto;
			}
			else{
				if(passwordEncoder.matches(customerDto.getPassword(), customer.getPassword())) {	
					return customerDto.customerDto(0l, customer.getFirstname(), customer.getLastname(), customer.getEmail(), customer.getPhone(), customer.getDob(), customer.getGender(), null);
				}
				else {
					// if the password doesn't match throw an error
					 throw new DataNotFound("Invalid Password!");
				}
			}
		}
		else {
			throw new InputInvalid("Input data required");
		}
			
	}
	


	/**
	 * Obtains the customer phone form the principal and updates the customer. 
	 * 
	 * @param principal		authentication header object.
	 * @param customerDto	CustomerDto object containing the information to be updated.
	 * @return				The updated customerDto object.
	 */
	@Override
	public CustomerDto updateCustomer(Principal principal, CustomerDto customerDto) {
		
		if(customerDto!=null && principal!=null && !principal.getName().isEmpty()) {
			customerDto.setPhone(principal.getName());
			Customer customer = customerRepo.findByPhone(customerDto.getPhone());
			if(customer==null) {
				throw new DataNotFound("Session Expired! login again!");
			}
			else {
				customer.setDob(customerDto.getDob());
				customer.setEmail(customerDto.getEmail());
				customer.setFirstname(customerDto.getFirstname());
				customer.setGender(customerDto.getGender());
				customer.setLastname(customerDto.getLastname());
				customerRepo.save(customer);
				return customerDto;
			}
		}
		else {
			throw new InputInvalid("Input data required");
		}
	}

	/**
	 * Returns the userDetails used for token generation.
	 * 
	 * @param	username	The user phone number
	 * @return				The saved user details.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Customer customer =  customerRepo.findByPhone(username);
		if(customer!=null) {
			CustomerDto cdto = CustomerDto.customerDto(0l, customer.getFirstname(), customer.getLastname(), customer.getEmail(), customer.getPhone(), customer.getDob(), customer.getGender(), customer.getPassword());
			return cdto;
		}
		else {
			throw new DataNotFound("User not registered!");
		}
	
	}

	/**
	 * Returns the customerDto object of the registered customer. 
	 * Used to display the customer details on the front end.
	 * 
	 * @param principal	
	 * @return			CustomerDto matching the customer phone fetched from the principal object. 		
	 */
	@Override
	public CustomerDto getProfiles(Principal principal) {
		if( principal!=null && !principal.getName().isEmpty()) {
			String phone = principal.getName();
			CustomerDto customerDto = new CustomerDto();
			Customer customer= customerRepo.findByPhone(phone);
			if(customer==null) {
				throw new DataNotFound("Error occured login again!");
			}
			else {
				customerDto.setDob(customer.getDob());
				customerDto.setEmail(customer.getEmail());
				customerDto.setFirstname(customer.getFirstname());
				customerDto.setGender(customer.getGender());
				customerDto.setLastname(customer.getLastname());
				customerDto.setPhone(customer.getPhone());
				return customerDto;
			}
		}
		else {
			throw new InputInvalid("Session timedout! Login again to continue");
		}
	
	}

}
