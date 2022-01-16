package com.kualkua.gangcompetition.repository;

import com.kualkua.gangcompetition.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
}