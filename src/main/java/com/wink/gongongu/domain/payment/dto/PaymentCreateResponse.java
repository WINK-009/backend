package com.wink.gongongu.domain.payment.dto;

import com.wink.gongongu.domain.payment.entity.PaymentMethod;
import com.wink.gongongu.domain.payment.entity.PaymentStatus;
import java.time.LocalDateTime;

public record PaymentCreateResponse(
    Long paymentId,
    Long userId,
    Long postId,
    Integer amount,
    PaymentMethod method,
    PaymentStatus status,
    LocalDateTime createdAt
) {
}
