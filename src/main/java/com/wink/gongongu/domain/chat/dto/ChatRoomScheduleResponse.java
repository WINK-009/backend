package com.wink.gongongu.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatRoomScheduleResponse(
    @Schema(description = "채팅방 ID", example = "1")
    Long chatRoomId,

    @Schema(description = "확정된 일정 시간", example = "2026-03-17T15:30:00")
    LocalDateTime scheduledAt,

    @Schema(description = "일정 확정 여부", example = "true")
    boolean confirmed,

    @Schema(description = "일정 확정자 사용자 ID", example = "3")
    Long confirmedByUserId,

    @Schema(description = "마지막 수정 시각", example = "2026-03-09T10:20:00")
    LocalDateTime updatedAt
) {
}