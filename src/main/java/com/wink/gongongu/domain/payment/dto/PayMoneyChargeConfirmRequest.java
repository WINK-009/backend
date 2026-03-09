package com.wink.gongongu.domain.payment.dto;

public record PayMoneyChargeConfirmRequest(
    String paymentKey,
    String orderId,
    Integer amount
) {
}
