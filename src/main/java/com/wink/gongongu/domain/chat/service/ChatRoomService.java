package com.wink.gongongu.domain.chat.service;

import com.wink.gongongu.domain.chat.dto.ChatMessageListResponse;
import com.wink.gongongu.domain.chat.dto.ChatMessageResponse;
import com.wink.gongongu.domain.chat.dto.ChatMessageSendRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomCreateResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomDetailResponse;
import com.wink.gongongu.domain.chat.dto.ChatRoomScheduleConfirmRequest;
import com.wink.gongongu.domain.chat.dto.ChatRoomScheduleResponse;
import com.wink.gongongu.domain.chat.entity.ChatMessage;
import com.wink.gongongu.domain.chat.entity.ChatRoom;
import com.wink.gongongu.domain.chat.entity.ChatRoomSchedule;
import com.wink.gongongu.domain.chat.entity.ChatType;
import com.wink.gongongu.domain.chat.exception.ChatErrorCode;
import com.wink.gongongu.domain.chat.repository.ChatMessageRepository;
import com.wink.gongongu.domain.chat.repository.ChatRoomRepository;
import com.wink.gongongu.domain.chat.repository.ChatRoomScheduleRepository;
import com.wink.gongongu.domain.participant.entity.Participant;
import com.wink.gongongu.domain.participant.repository.ParicipantRepository;
import com.wink.gongongu.domain.post.repository.PostRepository;
import com.wink.gongongu.global.exception.BusinessException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
    private final ChatRoomScheduleRepository chatRoomScheduleRepository;
    private final PostReader postReader;
    private final ParicipantRepository paricipantRepository;
    private final PostRepository postRepository;

    @Transactional
    public ChatRoomCreateResponse createChatRoom(ChatRoomCreateRequest request) {
        if (request == null || request.postId() == null || request.type() == null) {
            throw new BusinessException(ChatErrorCode.INVALID_CHAT_TYPE);
        }

        if (!postRepository.existsById(request.postId())) {
            throw new BusinessException(ChatErrorCode.POST_NOT_FOUND);
        }

        if (chatRoomRepository.existsByPostId(request.postId())) {
            throw new BusinessException(ChatErrorCode.CHAT_ROOM_ALREADY_EXISTS);
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

        Set<Long> participantIds = messages.stream()
            .map(ChatMessage::getParticipantId)
            .collect(Collectors.toSet());

        Map<Long, Participant> participantsById = paricipantRepository.findAllById(participantIds).stream()
            .collect(Collectors.toMap(Participant::getParticipantsId, p -> p, (left, right) -> left));

        List<ChatMessageResponse> items = messages.stream()
            .map(message -> {
                Participant participant = participantsById.get(message.getParticipantId());
                Long senderUserId = participant != null ? participant.getUserId().getId() : null;
                String senderNickname = participant != null ? participant.getUserId().getNickname() : null;

                return new ChatMessageResponse(
                    message.getChatMessageId(),
                    senderUserId,
                    senderNickname,
                    message.getContent(),
                    message.getCreatedAt()
                );
            })
            .toList();

        Long nextCursor = messages.hasNext() && !items.isEmpty()
            ? items.get(items.size() - 1).messageId()
            : null;

        return new ChatMessageListResponse(items, messages.hasNext(), nextCursor);
    }

    @Transactional
    public ChatMessageResponse sendChatMessage(Long chatRoomId, Long senderId, ChatMessageSendRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new BusinessException(ChatErrorCode.CHAT_ROOM_NOT_FOUND));

        if (request == null || senderId == null || request.content() == null || request.content().isBlank()) {
            throw new BusinessException(ChatErrorCode.INVALID_CHAT_MESSAGE);
        }

        Participant participant = paricipantRepository
            .findByUserId_IdAndPostId_PostIdAndDeletedFalse(senderId, chatRoom.getPostId())
            .orElseThrow(() -> new BusinessException(ChatErrorCode.CHAT_MESSAGE_FORBIDDEN));

        if (chatRoom.getType() == ChatType.BUSINESS && !participant.isIshost()) {
            throw new BusinessException(ChatErrorCode.CHAT_MESSAGE_FORBIDDEN);
        }

        ChatMessage message = ChatMessage.builder()
            .chatRoomId(chatRoomId)
            .participantId(participant.getParticipantsId())
            .content(request.content().trim())
            .build();

        ChatMessage saved = chatMessageRepository.save(message);

        return new ChatMessageResponse(
            saved.getChatMessageId(),
            participant.getUserId().getId(),
            participant.getUserId().getNickname(),
            saved.getContent(),
            saved.getCreatedAt()
        );
    }

    @Transactional
    public ChatRoomScheduleResponse confirmSchedule(
        Long chatRoomId,
        ChatRoomScheduleConfirmRequest request,
        Long confirmedByUserId
    ) {
        if (request == null || request.scheduledAt() == null) {
            throw new BusinessException(ChatErrorCode.INVALID_SCHEDULE_TIME);
        }

        if (!chatRoomRepository.existsById(chatRoomId)) {
            throw new BusinessException(ChatErrorCode.CHAT_ROOM_NOT_FOUND);
        }

        ChatRoomSchedule schedule = chatRoomScheduleRepository.findByChatRoomId(chatRoomId)
            .map(existing -> {
                existing.confirm(request.scheduledAt(), confirmedByUserId);
                return existing;
            })
            .orElseGet(() -> ChatRoomSchedule.builder()
                .chatRoomId(chatRoomId)
                .scheduledAt(request.scheduledAt())
                .confirmedByUserId(confirmedByUserId)
                .build());

        ChatRoomSchedule saved = chatRoomScheduleRepository.save(schedule);
        return toScheduleResponse(saved);
    }

    public ChatRoomScheduleResponse getSchedule(Long chatRoomId) {
        if (!chatRoomRepository.existsById(chatRoomId)) {
            throw new BusinessException(ChatErrorCode.CHAT_ROOM_NOT_FOUND);
        }

        return chatRoomScheduleRepository.findByChatRoomId(chatRoomId)
            .map(this::toScheduleResponse)
            .orElse(new ChatRoomScheduleResponse(chatRoomId, null, false, null, null));
    }

    private ChatRoomScheduleResponse toScheduleResponse(ChatRoomSchedule schedule) {
        return new ChatRoomScheduleResponse(
            schedule.getChatRoomId(),
            schedule.getScheduledAt(),
            true,
            schedule.getConfirmedByUserId(),
            schedule.getUpdatedAt()
        );
    }
}