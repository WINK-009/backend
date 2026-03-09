package com.wink.gongongu.domain.chat.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.chat.dto.ChatMessageListResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomDetailResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomScheduleConfirmRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomScheduleResponse;
import com.wink.gongongu.domain.chat.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/rooms")
public class ChatRoomController implements ChatRoomControllerSpec {

    private final ChatRoomService chatRoomService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatRoomCreateResponse createChatRoom(@RequestBody ChatRoomCreateRequest request) {
        return chatRoomService.createChatRoom(request);
    }

    @Override
    @GetMapping("/{chatRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoomDetailResponse getChatRoomDetail(
        @Parameter(description = "채팅방 ID", example = "1") @PathVariable Long chatRoomId
    ) {
        return chatRoomService.getChatRoomDetail(chatRoomId);
    }

    @Override
    @GetMapping("/{chatRoomId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public ChatMessageListResponse getChatMessages(
        @Parameter(description = "채팅방 ID", example = "1") @PathVariable Long chatRoomId,
        @Parameter(description = "다음 조회용 커서", example = "150") @RequestParam(required = false) Long cursor,
        @Parameter(description = "조회 건수", example = "20") @RequestParam(defaultValue = "20") int size
    ) {
        return chatRoomService.getChatMessages(chatRoomId, cursor, size);
    }

    @Override
    @PostMapping("/{chatRoomId}/schedule/confirm")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoomScheduleResponse confirmSchedule(
        @Parameter(description = "채팅방 ID", example = "1") @PathVariable Long chatRoomId,
        @RequestBody ChatRoomScheduleConfirmRequest request,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        Long confirmedByUserId = principal == null ? null : principal.userId();
        return chatRoomService.confirmSchedule(chatRoomId, request, confirmedByUserId);
    }

    @Override
    @GetMapping("/{chatRoomId}/schedule")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoomScheduleResponse getSchedule(
        @Parameter(description = "채팅방 ID", example = "1") @PathVariable Long chatRoomId
    ) {
        return chatRoomService.getSchedule(chatRoomId);
    }
}
