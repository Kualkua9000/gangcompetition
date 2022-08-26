package com.kualkua.gangcompetition.repository;

import com.kualkua.gangcompetition.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByUsername(String username);

    default void saveMemberRefresh(String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        Member member = findByUsername(currentUserName);
        member.setRefreshToken(token);
        save(member);
    }
}