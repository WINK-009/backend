package com.wink.gongongu.domain.chat.dto;

import java.util.List;

public record ChatMessageListResponse(
    List<ChatMessageResponse> messages,
    boolean hasNext,
    Long nextCursor
) {
}