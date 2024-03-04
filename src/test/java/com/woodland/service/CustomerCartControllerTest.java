package com.woodland.service;

import org.aspectj.lang.annotation.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.woodland.controller.CustomerCartControlller;


@WebMvcTest(CustomerCartControlller.class)
public class CustomerCartControllerTest {
	
	private MockMvc mockMvc;

    @Mock
    private WoodlandCartServices cartServices;

    @InjectMocks
    private CustomerCartControlller cartControlller;

    @Before(value = "")
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(cartControlller)
                .build();
    }

   

}
