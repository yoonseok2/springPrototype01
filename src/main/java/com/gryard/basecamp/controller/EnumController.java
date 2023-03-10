package com.gryard.basecamp.controller;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gryard.basecamp.constant.EnumMapper;
import com.gryard.basecamp.constant.EnumModel;
import com.gryard.basecamp.constant.EnumValue;
import com.gryard.basecamp.constant.UserRole;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EnumController {
	
	private final EnumMapper enumMapper;
	
	@GetMapping("/enum")
	public Map<String,Object> getEnum(){
		Map<String, Object> enums = new LinkedHashMap<>();
		Class userRole = UserRole.class;
		enums.put("userRole", userRole.getEnumConstants());
		
		return enums;
	}
	
	@GetMapping("/mapper")
	public Map<String,List<EnumValue>> getMapper() {
		return enumMapper.getAll();
	}
	
}
