package com.wink.gongongu.domain.user.mapper;

import com.wink.gongongu.auth.dto.LoginResponse;
import com.wink.gongongu.auth.dto.SignUpResponse;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.entity.UserType;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class UserMapper {

    public static User toEntity(String kakaoId){
        return User.builder()
            .kakaoId(kakaoId)
            .userType(UserType.TMP)
            .build();
    }

    public static SignUpResponse toSignUpResponse(User user, String accessToken) {
        return SignUpResponse.builder()
            .userId(user.getId())
            .role(user.getUserType())
            .nickname(user.getNickname())
            .region(user.getRegion())
            .businessCode(user.getBusinessCode())
            .accessToken(accessToken)
            .build();
    }

    public static LoginResponse toLoginResponse(User user, String accessToken) {
        return LoginResponse.builder()
            .userId(user.getId())
            .role(user.getUserType())
            .nickname(user.getNickname())
            .accessToken(accessToken)
            .build();
    }
}
