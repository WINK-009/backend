package com.wink.gongongu.domain.user.dto;

import lombok.Builder;

//TODO: 프로필 이미지 추가
public record UserProfileUpdateRequest(
    String nickname
) {

}
