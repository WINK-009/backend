package com.wink.gongongu.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatRoomScheduleConfirmRequest(
    @Schema(description = "\ud655\uc815\ud560 \uc77c\uc815 \uc2dc\uac04", example = "2026-03-17T15:30:00")
    LocalDateTime scheduledAt
) {
}