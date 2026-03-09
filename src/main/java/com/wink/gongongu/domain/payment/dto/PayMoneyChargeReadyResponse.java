package com.wink.gongongu.domain.payment.dto;

public record PayMoneyChargeReadyResponse(
    Long paymentId,
    String orderId,
    Integer amount,
    String status
) {
}
