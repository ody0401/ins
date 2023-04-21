package com.isns.security;

import com.isns.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUser extends User {

    private static final long serialVersionUID = 1L;

    private Member member;
    public CustomUser(String username, String password,
                      Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(Member member) {
        super(member.getMemberEmail(), member.getMemberPw(), member.getMemberAuthList().stream()
                .map(auth -> new SimpleGrantedAuthority(auth.getMemberAuth())).collect(Collectors.toList()));

        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
