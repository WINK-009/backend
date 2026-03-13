package com.wink.gongongu.domain.post.dto;

import com.wink.gongongu.domain.post.entity.PostImage;

public record PostImageResponse(
        Long imageId,
        String imageUrl,
        boolean isMain
) {
    public static PostImageResponse from(PostImage pi) {
        return new PostImageResponse(pi.getImageId(), pi.getImageUrl(), pi.isMain());
    }
}
