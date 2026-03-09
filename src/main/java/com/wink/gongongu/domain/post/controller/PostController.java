package com.wink.gongongu.domain.post.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.post.dto.PostDetailResponse;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import com.wink.gongongu.domain.post.dto.UploadPostRequest;
import com.wink.gongongu.domain.post.dto.UploadPostResponse;
import com.wink.gongongu.domain.post.entity.PostStatus;
import com.wink.gongongu.domain.post.entity.PostType;
import com.wink.gongongu.domain.post.repository.PostRepository;
import com.wink.gongongu.domain.post.service.PostService;
import com.wink.gongongu.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadPostResponse> postRegister(@AuthenticationPrincipal UserPrincipal principal, @RequestPart(value="image", required = false)MultipartFile image, @RequestPart("request") UploadPostRequest request) throws IOException {
        //Long userId = extractUserId(authentication);
        Long userId = principal.userId();
        Long postId = postService.postRegister(userId, image, request);
        return ResponseEntity.status(201).body(new UploadPostResponse(postId));
    }

    private Long extractUserId(Authentication auth) {
        Object principal = auth.getPrincipal();

        if (principal instanceof User u) {
            return u.getId(); // User 엔티티의 PK getter명에 맞춰서 (getId / getUserId 등)
        }

        // 혹시 다른 케이스 대비
        if (principal instanceof Long id) return id;
        if (principal instanceof String s) return Long.parseLong(s);
        if (principal instanceof UserDetails ud) return Long.parseLong(ud.getUsername());

        throw new IllegalStateException("Cannot extract userId from principal: " + principal);
    }

    /*
    @GetMapping
    public ResponseEntity<List<PostListResponse>> getPostList(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "20") int size) {
        List<PostListResponse> PostList = postService.getPostList(page, size);
        return ResponseEntity.ok(PostList);

    }
    */

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponse> getPostDetail(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostDetail(postId));
    }

    // 목록+검색+필터 통합
    @GetMapping
    public ResponseEntity<List<PostListResponse>> getPosts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) PostType type,
            @RequestParam(defaultValue = "OPEN") PostStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(
                postService.searchPosts(query, region, type, status, page, size).getContent()
        );
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@AuthenticationPrincipal UserPrincipal principal, @PathVariable Long postId){
        postService.deletePost(principal.userId(), postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/posts/created")
    public ResponseEntity<List<PostListResponse>> myPost(@AuthenticationPrincipal UserPrincipal principal){
        return ResponseEntity.ok(postService.myPost(principal.userId()));
    }
}
