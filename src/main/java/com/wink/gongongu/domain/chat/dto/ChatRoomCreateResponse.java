package com.wink.gongongu.domain.chat.dto;

import com.wink.gongongu.domain.chat.entity.ChatType;
import java.time.LocalDateTime;

public record ChatRoomCreateResponse(
        Long chatRoomId,
        Long postId,
        ChatType type,
        LocalDateTime createdAt// 개인 |기업
) {}
