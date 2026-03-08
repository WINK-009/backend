package com.wink.gongongu.domain.user.dto;

import com.wink.gongongu.domain.user.entity.UserType;
import lombok.Builder;

@Builder
public record SignUpResponse(
    Long userId,
    UserType role,
    String nickname,
    String region,
    String businessCode
) {

}
