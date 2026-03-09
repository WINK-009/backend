package com.wink.gongongu.domain.chat.repository;

import com.wink.gongongu.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByPostId(Long postId);
}
