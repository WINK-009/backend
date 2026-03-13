package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyChargeReadyRequest(
    @Schema(description = "충전 금액", example = "5000")
    Integer amount
) {
}