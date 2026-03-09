package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyChargeConfirmResponse(
    @Schema(description = "\uacb0\uc81c ID", example = "1")
    Long paymentId,

    @Schema(description = "\uc8fc\ubb38 ID", example = "4e8e7e2f43c243f19bfc7f4adf5bb0ef")
    String orderId,

    @Schema(description = "\ud1a0\uc2a4 \uacb0\uc81c \ud0a4", example = "test_payment_key_xxx")
    String paymentKey,

    @Schema(description = "\ucda9\uc804 \uae08\uc561", example = "5000")
    Integer amount,

    @Schema(description = "\uacb0\uc81c \uc0c1\ud0dc", example = "SUCCESS")
    String status,

    @Schema(description = "\ucda9\uc804 \ud6c4 \ud604\uc7ac \ud398\uc774\uba38\ub2c8 \uc794\uc561", example = "12000")
    Integer currentPayMoney
) {
}