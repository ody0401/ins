package com.isns.controller;

import com.isns.domain.Member;
import com.isns.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(Member member) {
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@Validated Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/join";
        }
        try {
            member.setMemberPw(passwordEncoder.encode(member.getMemberPw()));
            memberService.join(member);
        } catch (IllegalStateException e) {
            model.addAttribute("err", e.getMessage());
            return "user/join";
        } catch (Exception e) {
            log.error(e.getMessage());
            return "error/error";
        }
        return "redirect:login";
    }
    @GetMapping("login")
    public String loginForm(String error, String logout, Model model) {
        if (error != null) {
            model.addAttribute("err", "아이디 또는 비밀번호가 틀렸습니다.");
        }

        if (logout != null) {
            log.info("logout");
        }
        return "user/login";
    }

}
