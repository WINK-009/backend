package com.wink.gongongu.domain.chat.service;

import com.wink.gongongu.domain.chat.dto.ChatRoomCreateRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomDetailResponse;
import com.wink.gongongu.domain.chat.entity.ChatRoom;
import com.wink.gongongu.domain.chat.exception.ChatErrorCode;
import com.wink.gongongu.domain.chat.repository.ChatRoomRepository;
import com.wink.gongongu.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final PostReader postReader;
    // private final ParticipantRepository participantRepository; // inject when participant domain is ready

    @Transactional
    public ChatRoomCreateResponse createChatRoom(ChatRoomCreateRequest request) {
        if (chatRoomRepository.existsByPostId(request.postId())) {
            throw new BusinessException(ChatErrorCode.CHAT_ROOM_ALREADY_EXISTS);
        }

        // Validate that the post exists when post domain is integrated.
        // postReader.existsById(request.postId())
        //     .orElseThrow(() -> new BusinessException(PostErrorCode.POST_NOT_FOUND));

        if (request.type() == null) {
            throw new BusinessException(ChatErrorCode.INVALID_CHAT_TYPE);
        }

        ChatRoom chatRoom = ChatRoom.builder()
            .postId(request.postId())
            .type(request.type())
            .build();

        chatRoomRepository.save(chatRoom);

        return new ChatRoomCreateResponse(
            chatRoom.getChatRoomId(),
            chatRoom.getPostId(),
            chatRoom.getType(),
            chatRoom.getCreatedAt()
        );
    }

    public ChatRoomDetailResponse getChatRoomDetail(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new BusinessException(ChatErrorCode.CHAT_ROOM_NOT_FOUND));

        String postTitle = postReader.findTitleByPostId(chatRoom.getPostId()).orElse(null);

        // TODO: replace with participantRepository.countByChatRoomId(chatRoomId)
        Integer participantCount = null;

        return new ChatRoomDetailResponse(
            chatRoom.getChatRoomId(),
            postTitle,
            chatRoom.getType(),
            participantCount,
            chatRoom.getCreatedAt()
        );
    }
}