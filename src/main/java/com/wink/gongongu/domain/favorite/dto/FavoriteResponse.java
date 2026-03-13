package com.wink.gongongu.domain.favorite.dto;

public record FavoriteResponse(
        Long postId,
        boolean islike,
        long likeCount
) {
}
