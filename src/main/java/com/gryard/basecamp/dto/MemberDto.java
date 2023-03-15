package com.gryard.basecamp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.gryard.basecamp.constant.UserRole;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "memPassword")
public class MemberDto {
	
	@NotEmpty
	@Size(min = 4, message="최소 4글자 이상 입력해주세요.")
	private String memUserid;
	
	@NotEmpty(message = "값을 입력해주세요.")
	private String memPassword;
	
	@NotEmpty(message = "값을 입력해주세요.")
	private String memUsername;
	
	@NotEmpty(message = "값을 입력해주세요.")
	private String memPhone;
	
	@NotNull
	private UserRole roles;
	
	private String refreshToken;
}
