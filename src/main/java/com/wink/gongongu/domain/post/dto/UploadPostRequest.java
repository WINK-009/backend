package com.wink.gongongu.domain.post.dto;

import com.wink.gongongu.domain.post.entity.PostType;

import java.time.LocalDate;

public record UploadPostRequest(
        String image,
        String title,
        Integer price,
        LocalDate duedate,
        Integer maxQuantity,
        String description,
        String region,
        PostType postType
) {
}
