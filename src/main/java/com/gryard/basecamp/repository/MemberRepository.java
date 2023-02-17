package com.gryard.basecamp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gryard.basecamp.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemUserid(String id);

}