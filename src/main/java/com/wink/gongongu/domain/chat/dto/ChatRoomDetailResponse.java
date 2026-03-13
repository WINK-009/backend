package com.wink.gongongu.domain.chat.dto;

import com.wink.gongongu.domain.chat.entity.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatRoomDetailResponse(
    @Schema(description = "채팅방 ID", example = "1")
    Long chatRoomId,

    @Schema(description = "게시글 제목", example = "공동구매 함께 해요")
    String postTitle,

    @Schema(description = "채팅방 타입", example = "PERSONAL")
    ChatType type,

    @Schema(description = "참여자 수", example = "2")
    Integer participantCount,

    @Schema(description = "생성 시각", example = "2026-03-09T10:00:00")
    LocalDateTime createdAt
) {
}