package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyBalanceResponse(
    @Schema(description = "\ud604\uc7ac \ud398\uc774\uba38\ub2c8 \uc794\uc561", example = "12000")
    int payMoney
) {
}