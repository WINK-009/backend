package com.wink.gongongu.domain.chat.service;

import com.wink.gongongu.domain.chat.dto.ChatMessageListResponse;
import com.wink.gongongu.domain.chat.dto.ChatMessageResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomDetailResponse;
import com.wink.gongongu.domain.chat.entity.ChatMessage;
import com.wink.gongongu.domain.chat.entity.ChatRoom;
import com.wink.gongongu.domain.chat.exception.ChatErrorCode;
import com.wink.gongongu.domain.chat.repository.ChatMessageRepository;
import com.wink.gongongu.domain.chat.repository.ChatRoomRepository;
import com.wink.gongongu.global.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomService {

    private static final int DEFAULT_MESSAGE_PAGE_SIZE = 20;
    private static final int MAX_MESSAGE_PAGE_SIZE = 100;

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final PostReader postReader;
    // private final ParticipantRepository participantRepository; // inject when participant domain is ready

    @Transactional
    public ChatRoomCreateResponse createChatRoom(ChatRoomCreateRequest request) {
        if (chatRoomRepository.existsByPostId(request.postId())) {
            throw new BusinessException(ChatErrorCode.CHAT_ROOM_ALREADY_EXISTS);
        }

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

    public ChatMessageListResponse getChatMessages(Long chatRoomId, Long cursor, int size) {
        if (!chatRoomRepository.existsById(chatRoomId)) {
            throw new BusinessException(ChatErrorCode.CHAT_ROOM_NOT_FOUND);
        }

        int safeSize = size <= 0 ? DEFAULT_MESSAGE_PAGE_SIZE : Math.min(size, MAX_MESSAGE_PAGE_SIZE);
        Pageable pageable = PageRequest.of(0, safeSize);

        Slice<ChatMessage> messages = cursor == null
            ? chatMessageRepository.findByChatRoomIdOrderByChatMessageIdDesc(chatRoomId, pageable)
            : chatMessageRepository.findByChatRoomIdAndChatMessageIdLessThanOrderByChatMessageIdDesc(
                chatRoomId,
                cursor,
                pageable
            );

        List<ChatMessageResponse> items = messages.stream()
            .map(message -> new ChatMessageResponse(
                message.getChatMessageId(),
                message.getUserId(),
                null,
                message.getContent(),
                message.getCreatedAt()
            ))
            .toList();

        Long nextCursor = messages.hasNext() && !items.isEmpty()
            ? items.get(items.size() - 1).messageId()
            : null;

        return new ChatMessageListResponse(items, messages.hasNext(), nextCursor);
    }
}