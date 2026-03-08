package com.wink.gongongu.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.entity.PostStatus;
import com.wink.gongongu.domain.post.entity.PostType;
import com.wink.gongongu.domain.user.entity.User;

import java.time.LocalDate;

public record PostDetailResponse(
        Long postId,
        Long userId,
        String nickname,
        String profileImageUrl,
        String title,
        Integer price,
        Integer originalprice,
        LocalDate duedate,
        Integer maxQuantity,
        Integer joinedQuantitySum,
        Integer remainingQuantity,
        PostStatus status,
        String region,
        LocalDate createdAt,
        PostType type
) {
    public static PostDetailResponse from(Post p, int joinedSum){
        User u = p.getUserId();
        int remaining = Math.max(p.getMaxQuantity() - joinedSum, 0);
        return new PostDetailResponse(
                p.getPostId(),
                u.getId(),
                u.getNickname(),
                u.getProfileImageUrl(),
                p.getTitle(),
                p.getPrice(),
                p.getOriginalprice(),
                p.getDueDate(),
                p.getMaxQuantity(),
                joinedSum,
                remaining,
                p.getStatus(),
                p.getRegion(),
                p.getCreatedAt().toLocalDate(),
                p.getType()
        );
    }
}
