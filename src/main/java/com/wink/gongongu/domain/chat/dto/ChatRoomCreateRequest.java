package com.wink.gongongu.domain.chat.dto;

import com.wink.gongongu.domain.chat.entity.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;

public record ChatRoomCreateRequest(
    @Schema(description = "게시글 ID", example = "123")
    Long postId,

    @Schema(description = "채팅방 타입 (PERSONAL/BUSINESS)", example = "PERSONAL")
    ChatType type
) {
}