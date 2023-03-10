package com.gryard.basecamp.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.gryard.basecamp.constant.EnumMapper;
import com.gryard.basecamp.constant.UserRole;

@Order(1)
@Configuration
public class WebConfig {
	
	@Bean
	public EnumMapper enumMapper() {
		EnumMapper enumMapper = new EnumMapper();
		enumMapper.put("UserRole", UserRole.class);
		
		return enumMapper;
	}
}
