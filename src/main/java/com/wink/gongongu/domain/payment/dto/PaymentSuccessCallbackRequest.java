package com.wink.gongongu.domain.payment.dto;

public record PaymentSuccessCallbackRequest(
    String providerTxId
) {
}
