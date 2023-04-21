package com.isns.mapper;

import com.isns.domain.Liked;

public interface LikedMapper {

    public Liked getLiked(int postNo, int memberNo) throws Exception;

    public int getLikedCount(int postNo) throws Exception;

    public void save(int postNo, int memberNo) throws Exception;

    public void delete(int postNo, int memberNo) throws Exception;
}
