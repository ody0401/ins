package com.isns.mapper;

import com.isns.domain.Comment;

import java.util.List;

public interface CommentMapper {

    public List<Comment> getComments(int postNo) throws Exception;

    public void saveComment(Comment comment) throws Exception;
}
