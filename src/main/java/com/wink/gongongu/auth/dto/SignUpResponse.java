package com.wink.gongongu.auth.dto;

import com.wink.gongongu.domain.user.entity.UserType;
import lombok.Builder;

@Builder
public record SignUpResponse(
    Long userId,
    UserType role,
    String nickname,
    String region,
    String businessCode,
    String accessToken
) {

}
