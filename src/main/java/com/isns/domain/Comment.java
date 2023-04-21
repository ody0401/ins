package com.isns.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Comment {

    private int commentNo;

    private String commentContent;

    private int memberNo;

    private int postNo;
}
