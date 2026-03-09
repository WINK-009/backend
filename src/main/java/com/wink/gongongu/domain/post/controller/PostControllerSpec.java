package com.wink.gongongu.domain.post.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.post.dto.PostDetailResponse;
import com.wink.gongongu.domain.post.dto.PostListResponse;
import com.wink.gongongu.domain.post.dto.UploadPostRequest;
import com.wink.gongongu.domain.post.dto.UploadPostResponse;
import com.wink.gongongu.domain.post.entity.PostStatus;
import com.wink.gongongu.domain.post.entity.PostType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "게시물 작성 관련 API")
public interface PostControllerSpec {
    @Operation(
            summary = "공구 게시물 작성 API",
            description = """
            INDIVIDUAL 회원의 경우 original price 값 삭제 후 보내야합니다. 
            BUSINESS 회원의 경우 region 값 삭제 후 전송. 
         
            """
    )
    ResponseEntity<UploadPostResponse> postRegister(UserPrincipal principal, MultipartFile image, UploadPostRequest request) throws IOException;

    @Operation(
            summary = "공구 게시물 상세 조회 API"
    )
    ResponseEntity<PostDetailResponse> getPostDetail(Long postId);

    @Operation(
            summary = "공구 게시물 검색 API",
            description = """

            """
    )
    ResponseEntity<List<PostListResponse>> getPosts(
            String query,
            String region,
            PostType type,
            PostStatus status,
            int page,
            int size
    );

    @Operation(
            summary = "공구 게시물 삭제 API",
            description = """
                    본인것만 삭제 가능"""
    )
    ResponseEntity<Void> deletePost(UserPrincipal principal, Long postId);

    @Operation(
            summary = "본인이 작성한 공구 게시물 조회 API",
            description = """
                    본인것만 조회 가능"""
    )
    ResponseEntity<List<PostListResponse>> myPost(UserPrincipal principal);
}
