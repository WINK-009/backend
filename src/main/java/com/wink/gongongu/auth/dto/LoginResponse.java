package com.wink.gongongu.auth.dto;

import com.wink.gongongu.domain.user.entity.UserType;

public record LoginResponse(
    Long userId,
    String nickname,
    UserType role,
    String accessToken
) {

}
