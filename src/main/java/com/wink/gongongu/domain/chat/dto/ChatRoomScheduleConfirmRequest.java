package com.wink.gongongu.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatRoomScheduleConfirmRequest(
    @Schema(description = "확정할 일정 시간", example = "2026-03-17T15:30:00")
    LocalDateTime scheduledAt
) {
}