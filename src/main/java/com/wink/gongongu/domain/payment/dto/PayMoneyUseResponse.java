package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyUseResponse(
    @Schema(description = "\uacb0\uc81c \ub300\uc0c1 \uac8c\uc2dc\uae00 ID", example = "123")
    Long postId,

    @Schema(description = "\ucc28\uac10\ub41c \uae08\uc561", example = "3000")
    Integer usedAmount,

    @Schema(description = "\ucc28\uac10 \ud6c4 \ud604\uc7ac \ud398\uc774\uba38\ub2c8 \uc794\uc561", example = "9000")
    Integer currentPayMoney
) {
}