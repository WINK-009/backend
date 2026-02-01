package com.wink.gongongu.domain.chat.service;

import com.wink.gongongu.domain.chat.dto.ChatRoomCreateRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateResponse;
import com.wink.gongongu.domain.chat.entity.ChatRoom;
import com.wink.gongongu.domain.chat.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ChatRoomCreateResponse createChatRoom(ChatRoomCreateRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findByPostId(request.postId())
                .orElseGet(() -> {
                    ChatRoom newRoom = ChatRoom.builder()
                            .postId(request.postId())
                            .type(request.type())
                            .build();
                    return chatRoomRepository.save(newRoom);
                });

        return new ChatRoomCreateResponse(
                chatRoom.getChatRoomId(),
                chatRoom.getPostId(),
                chatRoom.getType(),
                chatRoom.getCreatedAt()
        );
    }
}
