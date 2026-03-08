package com.wink.gongongu.domain.chat.dto;

import java.time.LocalDateTime;

public record ChatRoomScheduleConfirmRequest(
    LocalDateTime scheduledAt
) {
}