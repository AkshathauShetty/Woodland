package com.woodland.security;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.AuthenticationProvider; 
import org.springframework.security.authentication.dao.DaoAuthenticationProvider; 
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration; 
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity; 
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; 
import org.springframework.security.config.http.SessionCreationPolicy; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.woodland.service.WoodlandServices;
import com.woodland.serviceImplementation.WoodlandServiceImpl;
  
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig { 
  
    @Autowired   
    private JwtAuthFilter authFilter; 

    @Bean
    public UserDetailsService userDetailsService() { 
        return new WoodlandServiceImpl(); 
    } 
    
 
    @SuppressWarnings("removal")
	@Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 
    	System.out.println("inside secconfig sec fil chain");
  return http.csrf((csrf) -> csrf.disable())
		  .authorizeHttpRequests((authorizeHttpRequests) ->
			authorizeHttpRequests
			 .requestMatchers("/woodland/register","woodland/getproducts","/woodland/brosweProduct/*","/woodland/generateTokens","/woodland/getCategories/*","woodland/saveCategories","/woodland/getFilteredProducts/*/*/*","woodland/getProductsByCategory/*/*").permitAll()
			 .requestMatchers("/woodland/update","/woodland/getProfile","/woodland/getProfiles/*","/woodland/myOrders","woodland/addtoCart","woodland/displayCart/*","woodland/getCartId","woodland/deletecart","woodland/getAddress","woodland/editAddress","woodland/delAddress/*").authenticated()
			 .requestMatchers("woodland/updateCart","woodland/saveAddress").authenticated()
			 .requestMatchers("woodland/saveOrder","woodland/deleteAddedcart","woodland/getPrevOrders","woodland/getRecentOrders","woodland/savePayment").authenticated()
			)
		  .sessionManagement((sessionManagement) ->
			sessionManagement
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
			) 
          .authenticationProvider(authenticationProvider()) 
          .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class) 
          .build(); 
} 
   
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
  
    @Bean
    public AuthenticationProvider authenticationProvider() { 
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
        authenticationProvider.setUserDetailsService(userDetailsService()); 
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider; 
    } 
  
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
        return config.getAuthenticationManager(); 
    } 
  
    
  
} 