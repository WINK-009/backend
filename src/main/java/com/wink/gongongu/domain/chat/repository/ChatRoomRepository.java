package com.wink.gongongu.domain.chat.repository;

import com.wink.gongongu.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 게시물당 채팅방 하나 -> postId로 채팅방 찾는 메서드 필요
    Optional<ChatRoom> findByPostId(Long postId);
}