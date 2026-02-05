package com.wink.gongongu.domain.post.controller;

import com.wink.gongongu.domain.post.dto.UploadPostRequest;
import com.wink.gongongu.domain.post.dto.UploadPostResponse;
import com.wink.gongongu.domain.post.service.PostService;
import com.wink.gongongu.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<UploadPostResponse> postRegister(@AuthenticationPrincipal User user, @RequestBody UploadPostRequest request){
        //Long userId = extractUserId(authentication);
        Long userId = user.getId();
        Long postId = postService.postRegister(userId, request);
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

}
