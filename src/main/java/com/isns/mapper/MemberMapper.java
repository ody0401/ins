package com.isns.mapper;

import com.isns.domain.Member;
import com.isns.domain.MemberAuth;

public interface MemberMapper {

    public void create(Member member) throws Exception;

    public void createAuth(MemberAuth auth) throws Exception;

    public Member readByEmail(String memberEmail) throws Exception;

    public Member getMember(int memberNo) throws Exception;
}
