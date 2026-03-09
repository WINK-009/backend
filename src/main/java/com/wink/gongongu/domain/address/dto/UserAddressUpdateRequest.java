package com.wink.gongongu.domain.address.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserAddressUpdateRequest(
    @Schema(example = "홍길동2")
    String recipient,

    @Schema(example = "010-1234-5678")
    String phoneNumber,

    @Schema(example = "서울특별시 성북구 정릉로 77")
    String roadAddress,

    @Schema(example = "123호")
    String detailAddress,

    @Schema(example = "02707")
    String zipCode,

    @Schema(example = "학교")
    String alias,

    @Schema(example = "교수님께 전달해주세요")
    String memo,

    @Schema(example = "true")
    boolean isDefault,

    @Schema(example = "37.6108")
    double latitude,

    @Schema(example = "126.9967")
    double longitude
    ) {

}
