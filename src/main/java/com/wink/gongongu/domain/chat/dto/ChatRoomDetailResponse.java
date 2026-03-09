package com.wink.gongongu.domain.chat.dto;

import com.wink.gongongu.domain.chat.entity.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatRoomDetailResponse(
    @Schema(description = "\ucc44\ud305\ubc29 ID", example = "1")
    Long chatRoomId,

    @Schema(description = "\uac8c\uc2dc\uae00 \uc81c\ubaa9", example = "\uacf5\ub3d9\uad6c\ub9e4 \ud568\uaed8 \ud574\uc694")
    String postTitle,

    @Schema(description = "\ucc44\ud305\ubc29 \ud0c0\uc785", example = "PERSONAL")
    ChatType type,

    @Schema(description = "\ucc38\uc5ec\uc790 \uc218", example = "2")
    Integer participantCount,

    @Schema(description = "\uc0dd\uc131 \uc2dc\uac01", example = "2026-03-09T10:00:00")
    LocalDateTime createdAt
) {
}