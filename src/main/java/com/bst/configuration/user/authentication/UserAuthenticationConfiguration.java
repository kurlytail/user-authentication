package com.bst.configuration.user.authentication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan("com.bst.user")
public class UserAuthenticationConfiguration {
	@Bean
	public PasswordEncoder createEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
