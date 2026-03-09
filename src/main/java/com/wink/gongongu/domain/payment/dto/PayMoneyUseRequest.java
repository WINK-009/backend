package com.wink.gongongu.domain.payment.dto;

public record PayMoneyUseRequest(
    Long postId,
    Integer amount
) {
}
