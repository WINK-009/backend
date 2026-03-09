package com.wink.gongongu.domain.chat.websocket;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.chat.dto.ChatMessageResponse;
import com.wink.gongongu.domain.chat.dto.ChatMessageSendRequest;
import com.wink.gongongu.domain.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatMessageSocketController {

    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/rooms/{chatRoomId}/messages")
    public void sendMessage(
        @DestinationVariable Long chatRoomId,
        ChatMessageSendRequest request,
        Authentication authentication
    ) {
        Long senderId = extractUserId(authentication);
        ChatMessageResponse response = chatRoomService.sendChatMessage(chatRoomId, senderId, request);
        messagingTemplate.convertAndSend("/topic/chat/rooms/" + chatRoomId, response);
    }

    private Long extractUserId(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw new AccessDeniedException("Unauthenticated websocket user");
        }
        return principal.userId();
    }
}
