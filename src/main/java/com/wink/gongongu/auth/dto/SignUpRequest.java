package com.wink.gongongu.auth.dto;

import com.wink.gongongu.domain.user.entity.UserType;

public record SignUpRequest(
    UserType role,
    String nickname,
    String region,
    String businessCode
) {

}
