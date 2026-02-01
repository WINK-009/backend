package com.wink.gongongu.domain.chat.dto;

import com.wink.gongongu.domain.chat.entity.ChatType;

public record ChatRoomCreateRequest(
        Long postId,
        ChatType type // 개인 | 기업
) {}
