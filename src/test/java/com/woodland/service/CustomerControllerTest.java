package com.woodland.service;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.header;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;



import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woodland.controller.CustomerController;
import com.woodland.dto.CustomerDto;
import com.woodland.exception.DataNotFound;
import com.woodland.repository.CustomerRepo;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(CustomerController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
//	@Autowired 
//	private CustomerRepo customerRepo;
//	@Autowired
//	private WoodlandServices woodlandServices;
	@Spy // mock it partially
	@InjectMocks
	private CustomerDto customerDto = new CustomerDto();
	private static ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void testPositiveRegisterExample() throws Exception{

		customerDto.setPhone("9879879876");
		customerDto.setPassword("Asdf1234@");
//		CustomerDto customerDtos =  woodlandServices.registerCustomer(customerDto);
//		assertNotNull(customerDtos);
		WoodlandServices mock = org.mockito.Mockito.mock(WoodlandServices.class);
		when(mock.registerCustomer(customerDto)).thenReturn(customerDto);
		String json = mapper.writeValueAsString(customerDto);
		 mockMvc.perform(
		            post("/woodland/register")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(json).accept(MediaType.APPLICATION_JSON))
		 			.andExpect(status().isOk())
	        		.andExpect(jsonPath("$.phone", Matchers.equalTo("9879879876")));
//	    verify(woodlandServices, times(1)).registerCustomer(customerDto);
//	    verifyNoMoreInteractions(woodlandServices);
		//Mockito.when(woodlandServices.registerCustomer(ArgumentMatchers.eq(customerDto))).thenReturn(customerDto);
//        String json = mapper.writeValueAsString(customerDto);
//        mockMvc.perform(post("/register")
//        		.contentType(MediaType.APPLICATION_JSON)
//        		.characterEncoding("utf-8")
//                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
//                .andExpect(jsonPath("$.phone", Matchers.equalTo("9879879876")));
    }
	
	@Test
	public void testNegativeRegisterExample() throws Exception{
		CustomerDto customerDto = new CustomerDto();
		customerDto.setPhone("9879879876");
		customerDto.setPassword("Asdf123");
//		CustomerDto customerDtos =  woodlandServices.registerCustomer(customerDto);
//		assertNull(customerDtos);
		
		WoodlandServices mock = org.mockito.Mockito.mock(WoodlandServices.class);
		when(mock.registerCustomer(customerDto)).thenThrow(DataNotFound.class);
		String json = mapper.writeValueAsString(customerDto);
		 mockMvc.perform(
		            post("/woodland/register")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(json).accept(MediaType.APPLICATION_JSON))
		 			.andExpect(status().isInternalServerError())
	        		.andExpect(jsonPath("$.message", Matchers.equalTo("Invalid Password!")));
	
		//Mockito.when(woodlandServices.registerCustomer(ArgumentMatchers.eq(customerDto))).thenReturn(customerDto);
//        String json = mapper.writeValueAsString(customerDto);
//        mockMvc.perform(post("/register")
//        		.contentType(MediaType.APPLICATION_JSON)
//        		.characterEncoding("utf-8")
//                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
//                .andExpect(jsonPath("$.phone", Matchers.equalTo("9879879876")));
    }

	@Test
	public void testPositiveGetprofileExample() throws Exception{

		customerDto.setPhone("9879879876");
		customerDto.setPassword("Asdf1234@");
		WoodlandServices mock = org.mockito.Mockito.mock(WoodlandServices.class);
		Principal mockPrincipal =  org.mockito.Mockito.mock(Principal.class);
//		when(mockPrincipal.getName()).thenReturn("9879879876");
		when(mock.getProfiles(mockPrincipal)).thenReturn(customerDto);
		
		String json = mapper.writeValueAsString(customerDto);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/woodland/generateTokens")
							.contentType(MediaType.APPLICATION_JSON)
							.content(json).accept(MediaType.APPLICATION_JSON))
		          			.andExpect(status().isOk()).andReturn();
		
		String token = result.getResponse().getContentAsString();
		
		 mockMvc.perform(
		            get("/woodland/getProfile")
		            .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
		            )
		 			.andExpect(status().isOk())
	        		.andExpect(jsonPath("$.phone", Matchers.equalTo("9879879876")));
    }
	
	@Test
	public void testNegativeGetprofileExample() throws Exception{
		customerDto.setPhone("9879879876");
		customerDto.setPassword("Asdf1234@");
		WoodlandServices mock = org.mockito.Mockito.mock(WoodlandServices.class);
		Principal mockPrincipal =  org.mockito.Mockito.mock(Principal.class);
//		when(mockPrincipal.getName()).thenReturn("9879879876");
		when(mock.getProfiles(mockPrincipal)).thenThrow(DataNotFound.class);
		
		String json = mapper.writeValueAsString(customerDto);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/woodland/generateTokens")
							.contentType(MediaType.APPLICATION_JSON)
							.content(json).accept(MediaType.APPLICATION_JSON))
		          			.andExpect(status().isOk()).andReturn();
		
		String token = result.getResponse().getContentAsString();
		
		 mockMvc.perform(
		            get("/woodland/getProfile")
		            )
		 			.andExpect(status().isForbidden());
   
	}
	
}
