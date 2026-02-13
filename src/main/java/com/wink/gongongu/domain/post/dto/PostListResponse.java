package com.wink.gongongu.domain.post.dto;

import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.entity.PostStatus;
import com.wink.gongongu.domain.post.entity.PostType;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.global.common.BaseTimeEntity;

import java.time.LocalDate;

public record PostListResponse(
        Long postId,
        Long userId,
        String title,
        Integer price,
        Integer originalprice,
        LocalDate duedate,
        Integer maxQuantity,
        PostStatus status,
        String region,
        LocalDate createdAt,
        PostType type
) {
    public static PostListResponse from(Post p) {
        return new PostListResponse(
                p.getPostId(),

                p.getUserId().getId(),

                p.getTitle(),
                p.getPrice(),
                p.getOriginalprice(),

                p.getDueDate(),
                p.getMaxQuantity(),
                p.getStatus(),
                p.getRegion(),
                p.getCreatedAt().toLocalDate(),
                p.getType()
        );
    }
}
