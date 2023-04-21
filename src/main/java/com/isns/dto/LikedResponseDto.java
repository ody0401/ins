package com.isns.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LikedResponseDto {

    private int likedCount;

    private boolean liked;
}
