package com.wink.gongongu.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wink.gongongu.domain.participant.entity.ParticipantStatus;
import com.wink.gongongu.domain.post.entity.Post;
import com.wink.gongongu.domain.post.entity.PostStatus;
import com.wink.gongongu.domain.post.entity.PostType;
import com.wink.gongongu.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public record PostDetailResponse(
        Long postId,
        Long userId,
        String nickname,
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
        java.util.List<PostImageResponse> images,
        Integer favCount,
        boolean isFaved,
        ParticipantStatus participantStatus
) {
    public static PostDetailResponse from(Post p, int joinedSum, java.util.List<PostImageResponse> images, int favoriteCount, boolean isFaved, ParticipantStatus participantStatus){
        User u = p.getUserId();
        Integer mq = p.getMaxQuantity();
        int max = (mq == null) ? 0 : mq;  // ✅ null-safe
        int remaining = Math.max(max - joinedSum, 0);
        return new PostDetailResponse(
                p.getPostId(),
                u.getId(),
                u.getNickname(),
                p.getTitle(),
                p.getPrice(),
                p.getOriginalprice(),
                p.getDueDate(),
                max,
                joinedSum,
                remaining,
                p.getStatus(),
                p.getRegion(),
                p.getCreatedAt().toLocalDate(),
                p.getType(),
                images,
                favoriteCount,
                isFaved,
                participantStatus

        );
    }
}
