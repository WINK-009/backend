package com.wink.gongongu.domain.chat.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.chat.dto.ChatMessageListResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomDetailResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomScheduleConfirmRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomScheduleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "채팅", description = "채팅방 생성, 메시지 조회, 일정 확정 API")
public interface ChatRoomControllerSpec {

    @Operation(summary = "채팅방 생성", description = "게시글에 대한 채팅방을 생성합니다.")
    ChatRoomCreateResponse createChatRoom(ChatRoomCreateRequest request);

    @Operation(summary = "채팅방 상세 조회", description = "채팅방 정보를 조회합니다.")
    ChatRoomDetailResponse getChatRoomDetail(Long chatRoomId);

    @Operation(summary = "채팅방 메시지 조회", description = "커서 기반으로 채팅방 메시지를 조회합니다.")
    ChatMessageListResponse getChatMessages(Long chatRoomId, Long cursor, int size);

    @Operation(summary = "채팅방 일정 확정", description = "채팅방의 거래 일정을 확정합니다.")
    ChatRoomScheduleResponse confirmSchedule(
        Long chatRoomId,
        ChatRoomScheduleConfirmRequest request,
        UserPrincipal principal
    );

    @Operation(summary = "채팅방 일정 조회", description = "확정된 채팅방 일정을 조회합니다.")
    ChatRoomScheduleResponse getSchedule(Long chatRoomId);
}