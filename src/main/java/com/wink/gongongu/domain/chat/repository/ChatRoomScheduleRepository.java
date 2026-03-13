package com.wink.gongongu.domain.chat.repository;

import com.wink.gongongu.domain.chat.entity.ChatRoomSchedule;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomScheduleRepository extends JpaRepository<ChatRoomSchedule, Long> {
    Optional<ChatRoomSchedule> findByChatRoomId(Long chatRoomId);
}