package com.wink.gongongu.domain.favorite.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Tag(name = "게시물 찜 기능  관련 API")
public interface FavoriteControllerSpec {
    @Operation(
            summary = "찜 하기 API",
            description = """
            
         
            """
    )
    ResponseEntity<Void> addFav(
            UserPrincipal principal,
            Long postId
    );

    @Operation(
            summary = "찜 취소 API",
            description = """
         
            """
    )
    ResponseEntity<Void> deleteFav(
            UserPrincipal principal,
            Long postId
    );

    @Operation(
            summary = "나의 찜한 게시물 조회 API",
            description = """
         
            """
    )
    ResponseEntity<List<PostListResponse>> myFav(@AuthenticationPrincipal UserPrincipal principal);
}
