package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyUseResponse(
    @Schema(description = "결제 대상 게시글 ID", example = "123")
    Long postId,

    @Schema(description = "차감된 금액", example = "3000")
    Integer usedAmount,

    @Schema(description = "차감 후 현재 페이머니 잔액", example = "9000")
    Integer currentPayMoney
) {
}