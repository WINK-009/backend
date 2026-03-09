package com.wink.gongongu.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record ChatRoomScheduleResponse(
    @Schema(description = "\ucc44\ud305\ubc29 ID", example = "1")
    Long chatRoomId,

    @Schema(description = "\ud655\uc815\ub41c \uc77c\uc815 \uc2dc\uac04", example = "2026-03-17T15:30:00")
    LocalDateTime scheduledAt,

    @Schema(description = "\uc77c\uc815 \ud655\uc815 \uc5ec\ubd80", example = "true")
    boolean confirmed,

    @Schema(description = "\uc77c\uc815 \ud655\uc815\uc790 \uc0ac\uc6a9\uc790 ID", example = "3")
    Long confirmedByUserId,

    @Schema(description = "\ub9c8\uc9c0\ub9c9 \uc218\uc815 \uc2dc\uac01", example = "2026-03-09T10:20:00")
    LocalDateTime updatedAt
) {
}