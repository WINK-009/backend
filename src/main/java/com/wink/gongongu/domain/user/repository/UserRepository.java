package com.wink.gongongu.domain.user.repository;

import com.wink.gongongu.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByKakaoId(String providerId);
}
