package com.isns.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Post {

    private int postNo;

    private String postContent;

    private Image image;

    private int memberNo;

}
