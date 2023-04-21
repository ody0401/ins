package com.isns.mapper;

import com.isns.domain.Image;
import com.isns.domain.Post;

import java.util.HashMap;
import java.util.List;

public interface PostMapper {

    public void savePost(Post post) throws Exception;

    public void saveImage(Image image) throws Exception;

    public List<Post> getAllPost() throws Exception;
    public Post getPost(int postNo) throws Exception;

    public void delete(int postNo) throws Exception;
    public void modify(Post post) throws Exception;
}
