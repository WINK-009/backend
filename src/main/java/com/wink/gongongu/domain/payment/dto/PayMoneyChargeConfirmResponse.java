package com.wink.gongongu.domain.payment.dto;

public record PayMoneyChargeConfirmResponse(
    Long paymentId,
    String orderId,
    String paymentKey,
    Integer amount,
    String status,
    Integer currentPayMoney
) {
}
