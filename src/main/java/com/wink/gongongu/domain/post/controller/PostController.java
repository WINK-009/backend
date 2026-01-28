package com.wink.gongongu.domain.post.controller;

import com.wink.gongongu.domain.post.dto.UploadPostRequest;
import com.wink.gongongu.domain.post.dto.UploadPostResponse;
import com.wink.gongongu.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<UploadPostResponse> postRegister(@RequestBody UploadPostRequest request){
        Long postId = postService.postRegister(request);
        return ResponseEntity.status(201).body(new UploadPostResponse(postId));
    }
}
