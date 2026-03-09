package com.wink.gongongu.domain.payment.dto;

import com.wink.gongongu.domain.payment.entity.PaymentMethod;

public record PaymentCreateRequest(
    Long postId,
    Integer amount,
    PaymentMethod method
) {
}
