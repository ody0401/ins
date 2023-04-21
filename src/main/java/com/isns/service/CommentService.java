package com.isns.service;

import com.isns.domain.Comment;
import com.isns.dto.CommentDto;

import java.util.List;

public interface CommentService {

    public void save(Comment comment) throws Exception;

    public List<CommentDto> getComments(int postNo) throws Exception;
}
