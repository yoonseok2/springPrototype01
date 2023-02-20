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
            .csrf().disable() // rest api이므로 basic auth 및 csrf 보안을 사용하지 않는다는 설정
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // JWT를 사용하기 때문에 세션을 사용하지 않는다는 설정
            .and()
            .exceptionHandling()
            	.authenticationEntryPoint(new RestAuthenticationEntryPoint())
            	.and()
            .authorizeRequests() 
            .antMatchers("/api/login").permitAll() // 해당 API에 대해서는 모든 요청을 허가한다는 설정
            .antMatchers("/api/test").hasRole("USER") // USER 권한이 있어야 요청할 수 있다는 설정
            .anyRequest().authenticated() // 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행하겠다는 설정
            .addFilterBefore(JwtExceptionFilter,new JwtAuthenticationFilter(jwtTokenProvider).getClass());
        return http.build();
    }
 
	// JWT를 사용하기 위해서는 기본적으로 password encoder가 필요(Bycrypt encoder를 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
	
}
