package com.wink.gongongu.domain.chat.dto;

import java.time.LocalDateTime;

public record ChatMessageResponse(
    Long messageId,
    Long senderId,
    String senderNickname,
    String content,
    LocalDateTime sentAt
) {
}