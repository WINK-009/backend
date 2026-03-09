package com.wink.gongongu.domain.payment.mapper;

import com.wink.gongongu.domain.payment.dto.PaymentCreateResponse;
import com.wink.gongongu.domain.payment.dto.PaymentDetailResponse;
import com.wink.gongongu.domain.payment.dto.PaymentListItemResponse;
import com.wink.gongongu.domain.payment.entity.Payment;

public final class PaymentMapper {

    private PaymentMapper() {
    }

    public static PaymentCreateResponse toCreateResponse(Payment payment) {
        return new PaymentCreateResponse(
            payment.getPaymentId(),
            payment.getUserId(),
            payment.getPostId(),
            payment.getAmount(),
            payment.getMethod(),
            payment.getStatus(),
            payment.getCreatedAt()
        );
    }

    public static PaymentDetailResponse toDetailResponse(Payment payment) {
        return new PaymentDetailResponse(
            payment.getPaymentId(),
            payment.getUserId(),
            payment.getPostId(),
            payment.getAmount(),
            payment.getMethod(),
            payment.getStatus(),
            payment.getProviderTxId(),
            payment.getCreatedAt(),
            payment.getUpdatedAt()
        );
    }

    public static PaymentListItemResponse toListItemResponse(Payment payment) {
        return new PaymentListItemResponse(
            payment.getPaymentId(),
            payment.getPostId(),
            payment.getAmount(),
            payment.getMethod(),
            payment.getStatus(),
            payment.getCreatedAt()
        );
    }
}
