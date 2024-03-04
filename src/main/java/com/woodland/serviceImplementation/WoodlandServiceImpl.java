package com.woodland.serviceImplementation;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.woodland.dto.AddressDto;
import com.woodland.dto.CategoryDto;
import com.woodland.dto.CustomerDto;
import com.woodland.dto.ProductPicSizesDto;
import com.woodland.dto.ProductPicturesdto;
import com.woodland.dto.ProductsDto;
import com.woodland.dto.SubcategoryDto;
import com.woodland.dto.SubcategoryTypesDto;
import com.woodland.entity.Address;
import com.woodland.entity.Category;
import com.woodland.entity.Customer;
import com.woodland.entity.ProductPicSizes;
import com.woodland.entity.ProductPictures;
import com.woodland.entity.Products;
import com.woodland.entity.Subcategory;
import com.woodland.entity.SubcategoryTypes;
import com.woodland.exception.ConversionFailed;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.InputInvalid;
import com.woodland.repository.AddressRepo;
import com.woodland.repository.CategoryRepo;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.ProductImageRepo;
import com.woodland.repository.ProductsRepo;
import com.woodland.repository.SubCategoryRepo;
import com.woodland.repository.SubcatTypesRepo;
import com.woodland.service.WoodlandServices;

import jakarta.persistence.Tuple;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
@Primary
public class WoodlandServiceImpl implements WoodlandServices,UserDetailsService {

	

	@Autowired
	private ProductsRepo productsRepo;
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public Double calculateFinalprice(Double productdtoPrice, Integer productdtoOffers) {
		return productdtoPrice-(productdtoPrice*productdtoOffers/100);
	}
	
	/**
	 * converts the list of products to list of product dto's
	 * @param list of products
	 * @return list of product dto
	 * name:productsTodto
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
	 *  registers a customer based on the input customer dto details.	
	 * @param customer Dto
	 * @return CustomerDto (required to generate token)
	 * name:registerCustomer
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
	 * updates the customer profile based on the input dto values
	 * @param customer Dto
	 * @return CustomerDto
	 * name:updateCustomer
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
	 * Returns the  user details used by token generation
	 * @param {String} username - The user phone number
	 * @return {CustomerDto} - The saved user details.
	 * customerdto implements userdetails hence can be returned.
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
	 * return the customer profile
	 * @param {CustomerDto} customerDto - customer dto object  containing the values to be updated. 
	 * @return  {CustomerDto} -  the updated customerdto result and a http status code
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
