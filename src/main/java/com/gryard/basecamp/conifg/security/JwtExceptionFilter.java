package com.gryard.basecamp.conifg.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT ���� ���� ���� ó�� ����
 * */
@Component
@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		 try {
			 filterChain.doFilter(request, response); // JwtAuthenticationFilter�� �̵�
        } catch (JwtException ex) {
            // JwtAuthenticationFilter���� ���� �߻��ϸ� �ٷ� setErrorResponse ȣ��
            setErrorResponse(request, response, ex);
        }
	}
	
	public void setErrorResponse(HttpServletRequest req, HttpServletResponse res, Throwable ex) throws IOException {
        
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        // ex.getMessage() ���� jwtException�� �߻���Ű�鼭 �Է��� �޼����� ����ִ�.
        body.put("message", ex.getMessage());
        body.put("path", req.getServletPath());
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(res.getOutputStream(), body);
        res.setStatus(HttpServletResponse.SC_OK);
    }

}
