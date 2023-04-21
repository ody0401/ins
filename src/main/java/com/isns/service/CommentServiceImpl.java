package com.isns.service;

import com.isns.domain.Comment;
import com.isns.domain.Member;
import com.isns.dto.CommentDto;
import com.isns.mapper.CommentMapper;
import com.isns.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;

    private final MemberMapper memberMapper;

    @Override
    public void save(Comment comment) throws Exception {

        commentMapper.saveComment(comment);
    }

    @Override
    public List<CommentDto> getComments(int postNo) throws Exception {

        List<Comment> comments = commentMapper.getComments(postNo);

        List<CommentDto> dtos = comments.stream().map((comment) -> {
            try {
                return toDto(comment);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        return dtos;
    }

    private CommentDto toDto(Comment comment) throws Exception {
        CommentDto dto = new CommentDto();

        dto.setContent(comment.getCommentContent());

        Member member = memberMapper.getMember(comment.getMemberNo());

        dto.setName(member.getMemberName());
        dto.setAvatar(member.getMemberAvatar());

        return dto;
    }
}
