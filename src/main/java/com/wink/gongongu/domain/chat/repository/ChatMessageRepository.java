package com.wink.gongongu.domain.chat.repository;

import com.wink.gongongu.domain.chat.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    Slice<ChatMessage> findByChatRoomIdOrderByChatMessageIdDesc(Long chatRoomId, Pageable pageable);

    Slice<ChatMessage> findByChatRoomIdAndChatMessageIdLessThanOrderByChatMessageIdDesc(
        Long chatRoomId,
        Long cursor,
        Pageable pageable
    );
}