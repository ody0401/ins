package com.isns.service;

import com.isns.domain.Image;
import com.isns.domain.Post;
import com.isns.dto.PostResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    public void upload(Post post, MultipartFile file) throws Exception;

    public PostResponseDto getPost(int postNo, int memberNo) throws Exception;

    public List<PostResponseDto> getAllPost(int memberNo) throws Exception;

    public void modify(Post post) throws Exception;
    public void delete(int postNo) throws Exception;

}
