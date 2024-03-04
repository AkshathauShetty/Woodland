package com.woodland;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
@SpringBootApplication
public class WoodlandSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(WoodlandSystemApplication.class, args);
	}
	
	@Bean
	@Primary
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
}
