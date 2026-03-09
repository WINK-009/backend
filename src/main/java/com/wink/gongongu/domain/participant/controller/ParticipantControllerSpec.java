package com.wink.gongongu.domain.participant.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.participant.dto.JoinPostResponse;
import com.wink.gongongu.domain.participant.dto.JoinRequest;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "게시물 참여 관련 API")
public interface ParticipantControllerSpec {

    @Operation(
            summary = "공구 게시물 참여 API",
            description = """
            수량은 최대 수량 이하로만 참여가능
         
            """
    )
    ResponseEntity<JoinPostResponse> joinPost(
            UserPrincipal principal,
            Long postId,
            JoinRequest req);

    @Operation(
            summary = "참여한 공구 게시물 조회 API",
            description = """
            본인이 작성한 것도 함께 조회됨
         
            """
    )
    ResponseEntity<List<PostListResponse>> joinedPostList(
            UserPrincipal principal
    );

    @Operation(
            summary = "참여 취소 (탈퇴) API",
            description = """
            본인의 게시물은 참여 취소 불가능  
         
            """
    )
    ResponseEntity<Void> deleteJoin(
            UserPrincipal principal,
            Long postId
    );
}
