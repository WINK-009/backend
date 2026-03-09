package com.wink.gongongu.domain.chat.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.chat.dto.ChatMessageListResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomDetailResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomScheduleConfirmRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomScheduleResponse;
import com.wink.gongongu.domain.chat.service.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "\ucc44\ud305", description = "\ucc44\ud305\ubc29 \uc0dd\uc131, \uba54\uc2dc\uc9c0 \uc870\ud68c, \uc77c\uc815 \ud655\uc815 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "\ucc44\ud305\ubc29 \uc0dd\uc131", description = "\uac8c\uc2dc\uae00\uc5d0 \ub300\ud55c \ucc44\ud305\ubc29\uc744 \uc0dd\uc131\ud569\ub2c8\ub2e4.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatRoomCreateResponse createChatRoom(@RequestBody ChatRoomCreateRequest request) {
        return chatRoomService.createChatRoom(request);
    }

    @Operation(summary = "\ucc44\ud305\ubc29 \uc0c1\uc138 \uc870\ud68c", description = "\ucc44\ud305\ubc29 \uc815\ubcf4\ub97c \uc870\ud68c\ud569\ub2c8\ub2e4.")
    @GetMapping("/{chatRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoomDetailResponse getChatRoomDetail(
        @Parameter(description = "\ucc44\ud305\ubc29 ID", example = "1") @PathVariable Long chatRoomId
    ) {
        return chatRoomService.getChatRoomDetail(chatRoomId);
    }

    @Operation(summary = "\ucc44\ud305\ubc29 \uba54\uc2dc\uc9c0 \uc870\ud68c", description = "\ucee4\uc11c \uae30\ubc18\uc73c\ub85c \ucc44\ud305\ubc29 \uba54\uc2dc\uc9c0\ub97c \uc870\ud68c\ud569\ub2c8\ub2e4.")
    @GetMapping("/{chatRoomId}/messages")
    @ResponseStatus(HttpStatus.OK)
    public ChatMessageListResponse getChatMessages(
        @Parameter(description = "\ucc44\ud305\ubc29 ID", example = "1") @PathVariable Long chatRoomId,
        @Parameter(description = "\ub2e4\uc74c \uc870\ud68c\uc6a9 \ucee4\uc11c", example = "150") @RequestParam(required = false) Long cursor,
        @Parameter(description = "\uc870\ud68c \uac74\uc218", example = "20") @RequestParam(defaultValue = "20") int size
    ) {
        return chatRoomService.getChatMessages(chatRoomId, cursor, size);
    }

    @Operation(summary = "\ucc44\ud305\ubc29 \uc77c\uc815 \ud655\uc815", description = "\ucc44\ud305\ubc29\uc758 \uac70\ub798 \uc77c\uc815\uc744 \ud655\uc815\ud569\ub2c8\ub2e4.")
    @PostMapping("/{chatRoomId}/schedule/confirm")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoomScheduleResponse confirmSchedule(
        @Parameter(description = "\ucc44\ud305\ubc29 ID", example = "1") @PathVariable Long chatRoomId,
        @RequestBody ChatRoomScheduleConfirmRequest request,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        Long confirmedByUserId = principal == null ? null : principal.userId();
        return chatRoomService.confirmSchedule(chatRoomId, request, confirmedByUserId);
    }

    @Operation(summary = "\ucc44\ud305\ubc29 \uc77c\uc815 \uc870\ud68c", description = "\ud655\uc815\ub41c \ucc44\ud305\ubc29 \uc77c\uc815\uc744 \uc870\ud68c\ud569\ub2c8\ub2e4.")
    @GetMapping("/{chatRoomId}/schedule")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoomScheduleResponse getSchedule(
        @Parameter(description = "\ucc44\ud305\ubc29 ID", example = "1") @PathVariable Long chatRoomId
    ) {
        return chatRoomService.getSchedule(chatRoomId);
    }
}