package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyBalanceResponse(
    @Schema(description = "현재 페이머니 잔액", example = "12000")
    int payMoney
) {
}