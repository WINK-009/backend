package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyUseRequest(
    @Schema(description = "\uacb0\uc81c \ub300\uc0c1 \uac8c\uc2dc\uae00 ID", example = "123")
    Long postId,

    @Schema(description = "\uc0ac\uc6a9\ud560 \ud398\uc774\uba38\ub2c8 \uae08\uc561", example = "3000")
    Integer amount
) {
}