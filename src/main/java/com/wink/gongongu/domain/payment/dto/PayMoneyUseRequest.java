package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyUseRequest(
    @Schema(description = "결제 대상 게시글 ID", example = "123")
    Long postId,

    @Schema(description = "사용할 페이머니 금액", example = "3000")
    Integer amount
) {
}