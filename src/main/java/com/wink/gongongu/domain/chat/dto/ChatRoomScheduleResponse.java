package com.wink.gongongu.domain.chat.dto;

import java.time.LocalDateTime;

public record ChatRoomScheduleResponse(
    Long chatRoomId,
    LocalDateTime scheduledAt,
    boolean confirmed,
    Long confirmedByUserId,
    LocalDateTime updatedAt
) {
}