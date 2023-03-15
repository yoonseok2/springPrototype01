package com.gryard.basecamp.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gryard.basecamp.constant.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Builder
@Entity
@Getter // jpa entity임을 알립니다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 인자없는 생성자를 자동으로 생성합니다.
@AllArgsConstructor // 인자를 모두 갖춘 생성자를 자동으로 생성합니다.
public class Member implements UserDetails {

	@Id // pk
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memId;
	
	@Column
	private String memUserid;
	
	@Column
	private String memPassword;
	
	@Column
	private String memUsername;
	
	@Column
	private String memPhone;
	
	@Column
	private String memRegistDatetime;
	
	@Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole roles;
	
	@Column
	private String refreshToken;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return memPassword;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return memUserid;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void encodePassword(PasswordEncoder passwordEncoder) {
		// TODO Auto-generated method stub
		this.memPassword = passwordEncoder.encode(this.memPassword);
	}

}
