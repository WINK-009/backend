package com.wink.gongongu.domain.post.dto;

import com.wink.gongongu.domain.favorite.repository.FavoriteRepository;
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
        Integer joinedQuantitySum,
        Integer remainingQuantity,
        PostStatus status,
        String region,
        LocalDate createdAt,
        PostType type,
        Integer favCount,
        String image
) {
    public static PostListResponse from(Post p) {
        return from(p, 0,0);
    }
    public static PostListResponse from(Post p, int joinedSum, int favoriteCount) {
        int remaining = Math.max(p.getMaxQuantity() - joinedSum, 0);

        return new PostListResponse(
                p.getPostId(),

                p.getUserId().getId(),

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
                p.getType(),
                favoriteCount,
                p.getImage()
        );
    }
}
