package com.wink.gongongu.domain.participant.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.participant.dto.JoinPostResponse;
import com.wink.gongongu.domain.participant.dto.JoinRequest;
import com.wink.gongongu.domain.participant.service.ParticipantService;
import com.wink.gongongu.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
}
