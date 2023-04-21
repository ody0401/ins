package com.isns.service;

import com.isns.domain.Member;
import com.isns.domain.MemberAuth;
import com.isns.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    @Transactional
    @Override
    public void join(Member member) throws Exception {

        Member savedMember = memberMapper.readByEmail(member.getMemberEmail());

        if (savedMember != null) {
            throw new IllegalStateException("이미 회원가입한 사용자입니다.");
        }

        memberMapper.create(member);

        MemberAuth auth = new MemberAuth();
        auth.setMemberNo(member.getMemberNo());
        auth.setMemberAuth("MEMBER");

        memberMapper.createAuth(auth);
    }
}
