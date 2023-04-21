package com.isns.service;

import com.isns.domain.*;
import com.isns.dto.PostResponseDto;
import com.isns.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    @Value("${upload.path}")
    private String uploadPath;

    private final PostMapper postMapper;

    private final MemberMapper memberMapper;

    private final LikedMapper likedMapper;

    @Transactional
    @Override
    public void upload(Post post, MultipartFile file) throws Exception {

        String imageSrc = "image/" + uploadFile(file.getOriginalFilename(), file.getBytes());

        postMapper.savePost(post);

        Image image = new Image();
        image.setPostNo(post.getPostNo());
        image.setImageSrc(imageSrc);

        postMapper.saveImage(image);
    }

    @Override
    public List<PostResponseDto> getAllPost(int memberNo) throws Exception {

        List<Post> posts = postMapper.getAllPost();

        List<PostResponseDto> dtos = new ArrayList<>();

        if (posts != null) {
            for(Post post: posts) {
                dtos.add(changeDto(post, memberNo));
            }
        }

        return dtos;
    }

    @Override
    public PostResponseDto getPost(int postNo, int memberNo) throws Exception {

        Post post = postMapper.getPost(postNo);

        PostResponseDto dto = changeDto(post, memberNo);

        return dto;
    }

    @Override
    public void modify(Post post) throws Exception {
        postMapper.modify(post);
    }

    @Override
    public void delete(int postNo) throws Exception {
        postMapper.delete(postNo);
    }

    private String uploadFile(String originalName, byte[] fileData) throws Exception {

        File checkFolder = new File(uploadPath);

        if (!checkFolder.isDirectory()) {
            log.info("{} 폴더 생성", checkFolder.getPath());
            checkFolder.mkdirs();
        }

        UUID uid = UUID.randomUUID();

        String createdFileName = uid + "_" + originalName;

        File target = new File(uploadPath, createdFileName);

        FileCopyUtils.copy(fileData, target);

        return createdFileName;
    }

    private PostResponseDto changeDto(Post post, int memberNo) throws Exception {

        PostResponseDto dto = new PostResponseDto();

        dto.setPostNo(post.getPostNo());
        dto.setPostContent(post.getPostContent());

        Member member = memberMapper.getMember(post.getMemberNo());

        dto.setMemberName(member.getMemberName());
        dto.setMemberAvatar(member.getMemberAvatar());
        dto.setMemberEmail(member.getMemberEmail());


        dto.setImageSrc(post.getImage().getImageSrc());

        dto.setLikedCount(likedMapper.getLikedCount(post.getPostNo()));

        Liked liked = likedMapper.getLiked(post.getPostNo(), memberNo);
        dto.setLiked(liked != null);

        return dto;
    }
}
