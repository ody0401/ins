package com.isns.controller;

import com.isns.domain.Member;
import com.isns.dto.LikedResponseDto;
import com.isns.security.CustomUser;
import com.isns.service.LikedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/liked")
public class LikedController {

    private final LikedService likedService;

    @GetMapping("/{postNo}")
    public ResponseEntity getLiked(Authentication authentication,@PathVariable int postNo) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        try {
            LikedResponseDto dto = likedService.getLiked(postNo, member.getMemberNo());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("")
    public ResponseEntity toggle(Authentication authentication, int postNo) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        Member member = customUser.getMember();

        try {
            LikedResponseDto dto = likedService.toggle(postNo, member.getMemberNo());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }


    }
}
