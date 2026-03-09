package com.wink.gongongu.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ChatMessageListResponse(
    @Schema(description = "\uba54\uc2dc\uc9c0 \ubaa9\ub85d")
    List<ChatMessageResponse> messages,

    @Schema(description = "\ub2e4\uc74c \ud398\uc774\uc9c0 \uc874\uc7ac \uc5ec\ubd80", example = "true")
    boolean hasNext,

    @Schema(description = "\ub2e4\uc74c \uc870\ud68c\uc6a9 \ucee4\uc11c", example = "145")
    Long nextCursor
) {
}