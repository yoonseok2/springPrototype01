package com.gryard.basecamp.conifg.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
	
	private final JwtTokenProvider jwtTokenProvider;
	
	// Request�� ������ Jwt Token�� ��ȿ���� ����(jwtTokenProvider.validateToken)�ϴ� filter�� filterChain�� ����մϴ�.
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		// 1. Request Header ���� JWT ��ū ����
        String token = resolveToken((HttpServletRequest) request);
 
        // 2. validateToken ���� ��ū ��ȿ�� �˻�
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // ��ū�� ��ȿ�� ��� ��ū���� Authentication ��ü�� ������ �ͼ� SecurityContext �� ����
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
		
	}
	
	// Request Header ���� ��ū ���� ����
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
