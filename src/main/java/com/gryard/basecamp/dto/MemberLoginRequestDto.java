package com.gryard.basecamp.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
	
	private String memUserid;
	private String memPassword;
}
