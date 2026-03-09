package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyChargeConfirmRequest(
    @Schema(description = "\ud1a0\uc2a4 \uacb0\uc81c \ud0a4", example = "test_payment_key_xxx")
    String paymentKey,

    @Schema(description = "\uc8fc\ubb38 ID", example = "4e8e7e2f43c243f19bfc7f4adf5bb0ef")
    String orderId,

    @Schema(description = "\ucda9\uc804 \uae08\uc561", example = "5000")
    Integer amount
) {
}