package com.wink.gongongu.domain.user;

import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class UserInitializer implements ApplicationRunner {

    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
         User testUser1 = User.builder()
            .nickname("테스트유저")
            .build();

         userRepository.save(testUser1);

    }
}
