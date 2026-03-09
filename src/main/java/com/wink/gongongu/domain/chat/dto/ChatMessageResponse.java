package com.wink.gongongu.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatMessageResponse(
    @Schema(description = "\uba54\uc2dc\uc9c0 ID", example = "101")
    Long messageId,

    @Schema(description = "\ubcf4\ub0b8 \uc0ac\uc6a9\uc790 ID", example = "3")
    Long senderId,

    @Schema(description = "\ubcf4\ub0b8 \uc0ac\uc6a9\uc790 \ub2c9\ub124\uc784", example = "\ucca0\uc218")
    String senderNickname,

    @Schema(description = "\uba54\uc2dc\uc9c0 \ub0b4\uc6a9", example = "\uc548\ub155\ud558\uc138\uc694")
    String content,

    @Schema(description = "\uc804\uc1a1 \uc2dc\uac01", example = "2026-03-09T10:01:00")
    LocalDateTime sentAt
) {
}