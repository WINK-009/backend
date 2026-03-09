package com.wink.gongongu.domain.payment.dto;

public record PayMoneyUseResponse(
    Long postId,
    Integer usedAmount,
    Integer currentPayMoney
) {
}
