package com.wink.gongongu.domain.chat.dto;

import com.wink.gongongu.domain.chat.entity.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatRoomCreateResponse(
    @Schema(description = "채팅방 ID", example = "1")
    Long chatRoomId,

    @Schema(description = "게시글 ID", example = "123")
    Long postId,

    @Schema(description = "채팅방 타입", example = "PERSONAL")
    ChatType type,

    @Schema(description = "생성 시각", example = "2026-03-09T10:00:00")
    LocalDateTime createdAt
) {
}