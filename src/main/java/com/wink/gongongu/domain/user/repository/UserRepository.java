package com.wink.gongongu.domain.user.repository;

import com.wink.gongongu.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
