package com.wink.gongongu.domain.address.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserAddressCreateRequest(
    @Schema(example = "홍길동")
    String recipient,

    @Schema(example = "010-1234-5678")
    String phoneNumber,

    @Schema(example = "서울특별시 강남구 테헤란로 123")
    String roadAddress,

    @Schema(example = "강남빌딩 5층")
    String detailAddress,

    @Schema(example = "12345")
    String zipCode,

    @Schema(example = "37.123456")
    double latitude,

    @Schema(example = "127.123456")
    double longitude,

    @Schema(nullable = true, example = "집")
    String alias,

    @Schema(nullable = true, example = "배송 시 벨을 눌러주세요.")
    String memo,

    @Schema(example = "true")
    boolean isDefault
) {

}
