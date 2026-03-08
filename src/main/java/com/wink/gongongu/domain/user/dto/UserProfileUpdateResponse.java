package com.wink.gongongu.domain.user.dto;


import lombok.Builder;

@Builder
public record UserProfileUpdateResponse(
    String profileImageUrl,
    String nickname
) {

}
