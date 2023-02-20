package com.gryard.basecamp.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.gryard.basecamp.conifg.security.JwtAuthenticationFilter;
import com.gryard.basecamp.conifg.security.JwtExceptionFilter;
import com.gryard.basecamp.conifg.security.JwtTokenProvider;
import com.gryard.basecamp.conifg.security.RestAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtExceptionFilter JwtExceptionFilter;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable() // rest api�̹Ƿ� basic auth �� csrf ������ ������� �ʴ´ٴ� ����
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT�� ����ϱ� ������ ������ ������� �ʴ´ٴ� ����
            .and()
            .exceptionHandling()
            	.authenticationEntryPoint(new RestAuthenticationEntryPoint())
            	.and()
            .authorizeRequests() 
            .antMatchers("/api/login").permitAll() // �ش� API�� ���ؼ��� ��� ��û�� �㰡�Ѵٴ� ����
            .antMatchers("/api/test").hasRole("USER") // USER ������ �־�� ��û�� �� �ִٴ� ����
            .anyRequest().authenticated() // �� �ۿ� ��� ��û�� ���ؼ� ������ �ʿ�� �Ѵٴ� ����
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // JWT ������ ���Ͽ� ���� ������ ���͸� UsernamePasswordAuthenticationFilter ���� �����ϰڴٴ� ����
            .addFilterBefore(JwtExceptionFilter,new JwtAuthenticationFilter(jwtTokenProvider).getClass());
        return http.build();
    }
 
	// JWT�� ����ϱ� ���ؼ��� �⺻������ password encoder�� �ʿ�(Bycrypt encoder�� ���)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
}
