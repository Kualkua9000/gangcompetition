package com.kualkua.gangcompetition.service;

import com.kualkua.gangcompetition.domain.Member;
import com.kualkua.gangcompetition.domain.Role;
import com.kualkua.gangcompetition.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MemberService implements UserDetailsService {

    final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return memberRepository.findByUsername(username);
    }

    public Member registrationUser(Member user) {
        Member member = memberRepository.findByUsername(user.getUsername());
        if (member == null) {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            memberRepository.save(user);
            return user;
        }
        return null;
    }
}
