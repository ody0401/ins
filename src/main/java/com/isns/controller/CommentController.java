package com.isns.controller;

import com.isns.domain.Comment;
import com.isns.domain.Member;
import com.isns.dto.CommentDto;
import com.isns.security.CustomUser;
import com.isns.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("comment")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{postNo}")
    public ResponseEntity getComments(@PathVariable int postNo) {
        try {
            List<CommentDto> dtos = commentService.getComments(postNo);
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{postNo}")
    public ResponseEntity save(Authentication authentication, Comment comment) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        comment.setMemberNo(member.getMemberNo());

        try {
            commentService.save(comment);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
