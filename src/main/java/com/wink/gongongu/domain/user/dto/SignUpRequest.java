package com.wink.gongongu.domain.user.dto;

import com.wink.gongongu.domain.user.entity.UserType;
import io.swagger.v3.oas.annotations.media.Schema;

public record SignUpRequest(
    @Schema(example = "INDIVIDUAL", description = "INDIVIDUAL(개인형), BUSINESS(기업형) 중 하나")
    UserType role,

    @Schema(example = "홍길동", description = "사용자 닉네임")
    String nickname,

    @Schema(example = "서울특별시 강남구", description = "사용자 지역(거래 행정구역), 개인형인 경우만 필수")
    String region,

    @Schema(nullable = true, example = "1234567890", description = "사업자 등록 번호(기업형일 경우에만 필수)")
    String businessCode
) {

}
