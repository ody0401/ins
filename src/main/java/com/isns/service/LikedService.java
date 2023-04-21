package com.isns.service;

import com.isns.dto.LikedResponseDto;

public interface LikedService {

    public LikedResponseDto getLiked(int postNo, int memberNo) throws Exception;

    public LikedResponseDto toggle(int postNo, int memberNo) throws Exception;
}
