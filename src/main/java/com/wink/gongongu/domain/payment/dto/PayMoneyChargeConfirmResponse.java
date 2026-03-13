package com.wink.gongongu.domain.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record PayMoneyChargeConfirmResponse(
    @Schema(description = "결제 ID", example = "1")
    Long paymentId,

    @Schema(description = "주문 ID", example = "4e8e7e2f43c243f19bfc7f4adf5bb0ef")
    String orderId,

    @Schema(description = "토스 결제 키", example = "test_payment_key_xxx")
    String paymentKey,

    @Schema(description = "충전 금액", example = "5000")
    Integer amount,

    @Schema(description = "결제 상태", example = "SUCCESS")
    String status,

    @Schema(description = "충전 후 현재 페이머니 잔액", example = "12000")
    Integer currentPayMoney
) {
}