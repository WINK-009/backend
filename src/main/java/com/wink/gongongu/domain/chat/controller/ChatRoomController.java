package com.wink.gongongu.domain.chat.controller;

import com.wink.gongongu.domain.chat.dto.ChatRoomCreateRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomDetailResponse;
import com.wink.gongongu.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat/rooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChatRoomCreateResponse createChatRoom(@RequestBody ChatRoomCreateRequest request) {
        return chatRoomService.createChatRoom(request);
    }

    @GetMapping("/{chatRoomId}")
    @ResponseStatus(HttpStatus.OK)
    public ChatRoomDetailResponse getChatRoomDetail(@PathVariable Long chatRoomId) {
        return chatRoomService.getChatRoomDetail(chatRoomId);
    }
}
