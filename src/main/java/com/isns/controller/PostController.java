package com.isns.controller;

import com.isns.domain.Member;
import com.isns.domain.Post;
import com.isns.security.CustomUser;
import com.isns.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("post")
public class PostController {

    private final PostService postService;

    @ResponseBody
    @GetMapping("/{postNo}")
    public ResponseEntity getPost(Authentication authentication, @PathVariable int postNo) throws Exception {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        return ResponseEntity.ok(postService.getPost(postNo, member.getMemberNo()));
    }

    @ResponseBody
    @PostMapping("/upload")
    public ResponseEntity upload(Authentication authentication, Post post, MultipartFile file) throws Exception {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        post.setMemberNo(member.getMemberNo());

        postService.upload(post, file);

        return ResponseEntity.ok("게시 완료");
    }

    @ResponseBody
    @PostMapping("/modify")
    public ResponseEntity modify(Post post) throws Exception {
        postService.modify(post);

        return ResponseEntity.ok("ok");

    }

    @DeleteMapping("/{postNo}")
    public ResponseEntity<Void> delete(@PathVariable int postNo) throws Exception {

        postService.delete(postNo);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
