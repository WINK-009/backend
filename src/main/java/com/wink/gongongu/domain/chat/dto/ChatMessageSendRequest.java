package com.wink.gongongu.domain.chat.dto;

public record ChatMessageSendRequest(
    Long senderId,
    String senderNickname,
    String content
) {
}