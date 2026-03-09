package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyChargeReadyRequest(
    @Schema(description = "\ucda9\uc804 \uae08\uc561", example = "5000")
    Integer amount
) {
}