package com.wink.gongongu.domain.chat.dto;

import com.wink.gongongu.domain.chat.entity.ChatType;
import io.swagger.v3.oas.annotations.media.Schema;

public record ChatRoomCreateRequest(
    @Schema(description = "\uac8c\uc2dc\uae00 ID", example = "123")
    Long postId,

    @Schema(description = "\ucc44\ud305\ubc29 \ud0c0\uc785 (PERSONAL/BUSINESS)", example = "PERSONAL")
    ChatType type
) {
}