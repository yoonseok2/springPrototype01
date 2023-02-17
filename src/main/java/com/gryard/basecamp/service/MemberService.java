package com.gryard.basecamp.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gryard.basecamp.conifg.security.JwtTokenProvider;
import com.gryard.basecamp.dto.TokenInfo;
import com.gryard.basecamp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	
	@Transactional
    public TokenInfo login(String memberId, String password) {
        // 1. Login ID/PW �� ������� Authentication ��ü ����
        // �̶� authentication �� ���� ���θ� Ȯ���ϴ� authenticated ���� false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
 
        // 2. ���� ���� (����� ��й�ȣ üũ)�� �̷������ �κ�
        // authenticate �ż��尡 ����� �� CustomUserDetailsService ���� ���� loadUserByUsername �޼��尡 ����
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
 
        // 3. ���� ������ ������� JWT ��ū ����
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);
 
        return tokenInfo;
    }
}
