package com.wink.gongongu.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatMessageResponse(
    @Schema(description = "메시지 ID", example = "101")
    Long messageId,

    @Schema(description = "보낸 사용자 ID", example = "3")
    Long senderId,

    @Schema(description = "보낸 사용자 닉네임", example = "철수")
    String senderNickname,

    @Schema(description = "메시지 내용", example = "안녕하세요")
    String content,

    @Schema(description = "전송 시각", example = "2026-03-09T10:01:00")
    LocalDateTime sentAt
) {
}