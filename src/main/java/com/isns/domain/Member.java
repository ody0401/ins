package com.isns.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Member {

    private int memberNo;

    private String memberName;

    private String memberEmail;

    private String memberPw;

    private String memberAvatar;

    private List<MemberAuth> memberAuthList;
}
