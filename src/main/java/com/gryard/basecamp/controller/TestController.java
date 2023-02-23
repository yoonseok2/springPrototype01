package com.gryard.basecamp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gryard.basecamp.dto.MemberLoginRequestDto;
import com.gryard.basecamp.dto.TokenInfo;
import com.gryard.basecamp.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 환경 설정 확인용 컨트롤러
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TestController {
	
	private final MemberService memberService;
	
	@PostMapping("/login")
	public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
		String memberId = memberLoginRequestDto.getMemUserid();
        String password = memberLoginRequestDto.getMemPassword();

        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
	}
	
	@PostMapping("/test")
	public String test() {
		
		return "test";
	}
}
