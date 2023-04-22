package com.isns.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.isns.aws.AwsS3;
import com.isns.domain.*;
import com.isns.dto.PostResponseDto;
import com.isns.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private final PostMapper postMapper;

    private final MemberMapper memberMapper;

    private final LikedMapper likedMapper;

    @Transactional
    @Override
    public void upload(Post post, MultipartFile multipartFile) throws Exception {

        File file = convertMultipartFileToFile(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File convert fail"));

        AwsS3 s3 = upload(file, "upload");

        postMapper.savePost(post);

        Image image = new Image();
        image.setPostNo(post.getPostNo());
        image.setImageSrc(s3.getPath());

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

        Post post = postMapper.getPost(postNo);

        String imageSrc = post.getImage().getImageSrc();

        String key = imageSrc.substring(imageSrc.indexOf("upload"));

        if (!amazonS3.doesObjectExist(bucket, key)) {
            throw new AmazonS3Exception("Object " + key + " does not exist!");
        }

        amazonS3.deleteObject(bucket, key);

        postMapper.delete(postNo);
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

    public Optional<File> convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("user.dir") + "/" + multipartFile.getOriginalFilename());

        if (file.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(file)){
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }


    private AwsS3 upload(File file, String dirName) {
        String key = randomFileName(file, dirName);
        String path = putS3(file, key);
        removeFile(file);

        return AwsS3
                .builder()
                .key(key)
                .path(path)
                .build();
    }

    private String randomFileName(File file, String dirName) {
        return dirName + "/" + UUID.randomUUID() + file.getName();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return getS3(bucket, fileName);
    }

    private String getS3(String bucket, String fileName) {
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeFile(File file) {
        file.delete();
    }
}
