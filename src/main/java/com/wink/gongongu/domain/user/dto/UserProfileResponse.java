package com.wink.gongongu.domain.user.dto;

import com.wink.gongongu.domain.user.entity.UserType;
import lombok.Builder;

@Builder
public record UserProfileResponse(
    String nickname,
    String region,
    UserType role,
    int payMoney,
    String businessCode,
    String profileImageUrl
) {

}
