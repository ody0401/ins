package com.isns.dto;

import com.isns.domain.Liked;
import com.isns.domain.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PostResponseDto {

    private int postNo;

    private String postContent;

    private String imageSrc;

    private String memberAvatar;

    private String memberName;

    private String memberEmail;

    private int likedCount;

    private boolean liked;
}
