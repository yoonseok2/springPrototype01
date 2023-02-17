package com.gryard.basecamp.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gryard.basecamp.domain.Member;
import com.gryard.basecamp.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	log.info(username);
        return memberRepository.findByMemUserid(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("�ش��ϴ� ������ ã�� �� �����ϴ�."));
    }
 
    // �ش��ϴ� User �� �����Ͱ� �����Ѵٸ� UserDetails ��ü�� ���� ����
    private UserDetails createUserDetails(Member member) {
    	log.info(member.getUsername());
    	log.info(member.getPassword());
        return User.builder()
                .username(member.getUsername())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles(member.getRoles().toArray(new String[0]))
                .build();
    }
    
}
