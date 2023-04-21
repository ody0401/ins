package com.isns.service;

import com.isns.domain.Liked;
import com.isns.dto.LikedResponseDto;
import com.isns.mapper.LikedMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikedServiceImpl implements LikedService{

    private final LikedMapper likedMapper;

    @Transactional
    @Override
    public LikedResponseDto toggle(int postNo, int memberNo) throws Exception {

        LikedResponseDto dto = new LikedResponseDto();

        Liked liked = likedMapper.getLiked(postNo, memberNo);

        if (liked == null) {
            likedMapper.save(postNo, memberNo);
            dto.setLiked(true);
        } else {
            likedMapper.delete(postNo, memberNo);
            dto.setLiked(false);
        }

        dto.setLikedCount(likedMapper.getLikedCount(postNo));

        return dto;
    }

    @Override
    public LikedResponseDto getLiked(int postNo, int memberNo) throws Exception {

        LikedResponseDto dto = new LikedResponseDto();

        dto.setLiked(likedMapper.getLiked(postNo, memberNo) != null ? true: false);
        dto.setLikedCount(likedMapper.getLikedCount(postNo));

        return dto;


    }
}
