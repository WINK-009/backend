package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyChargeConfirmRequest(
    @Schema(description = "토스 결제 키", example = "test_payment_key_xxx")
    String paymentKey,

    @Schema(description = "주문 ID", example = "4e8e7e2f43c243f19bfc7f4adf5bb0ef")
    String orderId,

    @Schema(description = "충전 금액", example = "5000")
    Integer amount
) {
}