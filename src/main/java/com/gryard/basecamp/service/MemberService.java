package com.gryard.basecamp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.gryard.basecamp.domain.Member;
import com.gryard.basecamp.dto.TokenInfo;

public interface MemberService extends UserDetailsService {
	
	@Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    Member saveOrUpdateMember(Member member);
    
    TokenInfo login(String memberId, String password);
}
