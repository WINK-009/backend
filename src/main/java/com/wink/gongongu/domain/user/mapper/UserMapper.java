package com.wink.gongongu.domain.user.mapper;

import com.wink.gongongu.domain.user.entity.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserMapper {

    public static User toEntity(String kakaoId){
        return User.builder()
            .kakaoId(kakaoId)
            .build();
    }
}
