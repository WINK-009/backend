package com.wink.gongongu.domain.user.dto;

import com.wink.gongongu.domain.user.entity.UserType;
import lombok.Builder;

//TODO: 프로필 이미지 추가
@Builder
public record UserProfileResponse(
    String nickname,
    String region,
    UserType role,
    int payMoney,
    String businessCode
) {

}
