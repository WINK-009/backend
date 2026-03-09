package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyChargeReadyResponse(
    @Schema(description = "\uacb0\uc81c ID", example = "1")
    Long paymentId,

    @Schema(description = "\uc8fc\ubb38 ID", example = "4e8e7e2f43c243f19bfc7f4adf5bb0ef")
    String orderId,

    @Schema(description = "\ucda9\uc804 \uae08\uc561", example = "5000")
    Integer amount,

    @Schema(description = "\uacb0\uc81c \uc0c1\ud0dc", example = "READY")
    String status
) {
}