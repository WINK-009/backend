package com.wink.gongongu.domain.chat.dto;

import com.wink.gongongu.domain.chat.entity.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatRoomCreateResponse(
    @Schema(description = "\ucc44\ud305\ubc29 ID", example = "1")
    Long chatRoomId,

    @Schema(description = "\uac8c\uc2dc\uae00 ID", example = "123")
    Long postId,

    @Schema(description = "\ucc44\ud305\ubc29 \ud0c0\uc785", example = "PERSONAL")
    ChatType type,

    @Schema(description = "\uc0dd\uc131 \uc2dc\uac01", example = "2026-03-09T10:00:00")
    LocalDateTime createdAt
) {
}