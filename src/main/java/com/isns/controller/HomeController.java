package com.isns.controller;

import com.isns.domain.Member;
import com.isns.dto.PostResponseDto;
import com.isns.security.CustomUser;
import com.isns.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final PostService postService;
    @GetMapping("/")
    public String home(Authentication authentication, Model model) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        try {
            List<PostResponseDto> dtos = postService.getAllPost(member.getMemberNo());
            model.addAttribute("posts", dtos);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error/error";
        }

        return "home";
    }

}
