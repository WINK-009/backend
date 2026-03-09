package com.wink.gongongu.domain.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record ChatMessageListResponse(
    @Schema(description = "메시지 목록")
    List<ChatMessageResponse> messages,

    @Schema(description = "다음 페이지 존재 여부", example = "true")
    boolean hasNext,

    @Schema(description = "다음 조회용 커서", example = "145")
    Long nextCursor
) {
}