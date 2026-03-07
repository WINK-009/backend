package com.wink.gongongu.domain.chat.dto;

import com.wink.gongongu.domain.chat.entity.ChatType;
import java.time.LocalDateTime;

public record ChatRoomDetailResponse(
    Long chatRoomId,
    String postTitle,
    ChatType type,
    Integer participantCount,
    LocalDateTime createdAt
) {
}
