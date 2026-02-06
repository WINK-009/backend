package com.wink.gongongu.auth.dto;

import com.wink.gongongu.domain.user.entity.UserType;
import lombok.Builder;

@Builder
public record LoginResponse(
    Long userId,
    String nickname,
    UserType role,
    String accessToken
) {

}
