package com.woodland.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.woodland.controller.CustomerProductsController;
import com.woodland.dto.ProductsDto;
import com.woodland.exception.DataNotFound;
import com.woodland.exception.WoodlandExcepHandler;
import com.woodland.repository.CustomerRepo;
import com.woodland.repository.ProductsRepo;
import com.woodland.security.JwtAuthFilter;
import com.woodland.security.JwtService;
import com.woodland.security.SecurityConfig;


//@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers=CustomerProductsController.class)
@ContextConfiguration(classes={WoodlandExcepHandler.class, SecurityConfig.class})
public class CustomerProductsContorllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Primary
	public PasswordEncoder passwordEncoder () {
	  return new BCryptPasswordEncoder ();
	}

	@MockBean
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@MockBean
	private ProductsRepo productsRepo;
	
	@MockBean 
	private CustomerRepo customerRepo;
	
    @MockBean
    private WoodlandProductsServices productsServices;
    
    @MockBean
    private WoodlandCustomerServices woodlandServices;

    @MockBean
    private JwtService jwtService;
    
    @MockBean
    private JwtAuthFilter authFilter;
    
    @InjectMocks
    private CustomerProductsController productsController;

    @BeforeEach
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(productsController)
                .build();
    }
    
    @Test
    @Order(1)
    public void testPositiveGetProducts() throws Exception {
        
    	List<ProductsDto> productDtoList  =new ArrayList<>();   	
    	ProductsDto productsDto = ProductsDto.create(1L, 1000d, "Men", 10, 1200.45, "Yes", null, null, null);
    	ProductsDto productsDto2 = ProductsDto.create(1l, 1000d, "Men", 10, 1200.45, "Yes", null, null, null);
    	productDtoList.add(productsDto);
    	productDtoList.add(productsDto2);
        when(productsServices.getProducts()).thenReturn(productDtoList);
        this.mockMvc.perform(get("/woodland/getproducts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].suitabledtoFor", Matchers.equalTo("Men")));
        verify(productsServices, times(1)).getProducts();
        verifyNoMoreInteractions(productsServices);
    }

    @Test
    @Order(2)
    public void testNegativeGetProducts() throws Exception {	
       when(productsServices.getProducts()).thenThrow(new DataNotFound("Products Not Found!"));
        try {
        mockMvc.perform(
        		get("/woodland/getproducts"))
                .andExpect(status().isInternalServerError())
        		.andDo(print())
        		.andExpect(jsonPath("$.message", Matchers.equalTo("Products Not Found!")));
        }catch(Exception e) {
        	assertEquals("Request processing failed: com.woodland.exception.DataNotFound: Products Not Found!", e.getMessage());
        }
        verify(productsServices, times(1)).getProducts();
        verifyNoMoreInteractions(productsServices);
    }

    @Test
    @Order(3)
 public void testPositiveBrowse() throws Exception {
        
    	List<ProductsDto> productDtoList  =new ArrayList<>();   	
    	ProductsDto productsDto = ProductsDto.create(1L, 1000d, "Men", 10, 1200.45, "Yes", null, null, null);
    	ProductsDto productsDto2 = ProductsDto.create(1l, 1000d, "Women", 10, 1200.45, "Yes", null, null, null);
    	productDtoList.add(productsDto);
    	productDtoList.add(productsDto2);
        when(productsServices.browseProducts(anyString())).thenReturn(productDtoList);
        this.mockMvc.perform(
        		get("/woodland/brosweProduct/{categoryFilter}","Jackets"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
        		.andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].suitabledtoFor", Matchers.equalTo("Men")))
        		.andExpect(jsonPath("$[1].suitabledtoFor", Matchers.equalTo("Women")));
        verify(productsServices, times(1)).browseProducts(anyString());
        verifyNoMoreInteractions(productsServices);
    }

    @Test
    @Order(4)
    public void testNegativeBrowse() throws Exception {	
    	List<ProductsDto> productDtoList  =new ArrayList<>();   	
    	  when(productsServices.browseProducts(anyString())).thenReturn(productDtoList);
        try {
        mockMvc.perform(
        		get("/woodland/brosweProduct/{categoryFilter}","Jackets"))
                .andExpect(status().isNoContent())
        		.andDo(print());
        }catch(Exception e) {
         }
        verify(productsServices, times(1)).browseProducts(anyString());
        verifyNoMoreInteractions(productsServices);
    }

    
}
