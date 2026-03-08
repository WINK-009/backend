package com.wink.gongongu.domain.participant.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.participant.dto.JoinPostResponse;
import com.wink.gongongu.domain.participant.dto.JoinRequest;
import com.wink.gongongu.domain.participant.service.ParticipantService;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("/posts/{postId}/join")
    public ResponseEntity<JoinPostResponse> joinPost(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long postId,
            @RequestBody JoinRequest req) {
        return ResponseEntity.ok(participantService.joinPost(principal.userId(), postId, req));

    }

    @GetMapping("/users/posts/joined")
    public ResponseEntity<List<PostListResponse>> joinedPostList(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return ResponseEntity.ok(participantService.JoinedPostList(principal.userId()));
    }

    @DeleteMapping("/posts/{postId}/join")
    public ResponseEntity<Void> deletePost(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Long postId
    ){
        participantService.deleteJoin(principal.userId(), postId);
        return ResponseEntity.noContent().build();
    }
}
