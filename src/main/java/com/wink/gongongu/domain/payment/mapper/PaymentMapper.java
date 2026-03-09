package com.wink.gongongu.domain.payment.mapper;

import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyResponse;
import com.wink.gongongu.domain.payment.entity.Payment;

public final class PaymentMapper {

    private PaymentMapper() {
    }

    public static PayMoneyChargeReadyResponse toReadyResponse(Payment payment) {
        return new PayMoneyChargeReadyResponse(
            payment.getPaymentId(),
            payment.getOrderId(),
            payment.getAmount(),
            payment.getStatus().name()
        );
    }

    public static PayMoneyChargeConfirmResponse toConfirmResponse(Payment payment, int currentPayMoney) {
        return new PayMoneyChargeConfirmResponse(
            payment.getPaymentId(),
            payment.getOrderId(),
            payment.getPaymentKey(),
            payment.getAmount(),
            payment.getStatus().name(),
            currentPayMoney
        );
    }
}
