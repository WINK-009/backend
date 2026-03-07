package com.wink.gongongu.domain.user.mapper;

import com.wink.gongongu.domain.user.dto.SignUpResponse;
import com.wink.gongongu.domain.user.dto.UserProfileResponse;
import com.wink.gongongu.domain.user.dto.UserProfileUpdateResponse;
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

    public static SignUpResponse toSignUpResponse(User user) {
        return SignUpResponse.builder()
            .userId(user.getId())
            .role(user.getUserType())
            .nickname(user.getNickname())
            .region(user.getRegion())
            .businessCode(user.getBusinessCode())
            .build();
    }

    public static UserProfileResponse toUserProfileResponse(User user) {
        return UserProfileResponse.builder()
            .nickname(user.getNickname())
            .region(user.getRegion())
            .role(user.getUserType())
            .payMoney(user.getPayMoney())
            .businessCode(user.getBusinessCode())
            .profileImageUrl(user.getProfileImageUrl())
            .build();
    }

    public static UserProfileUpdateResponse toUserProfileUpdateResponse(User user) {
        return UserProfileUpdateResponse.builder()
            .profileImageUrl(user.getProfileImageUrl())
            .nickname(user.getNickname())
            .build();
    }
}
