package com.wink.gongongu.domain.payment.service;

import com.wink.gongongu.domain.payment.dto.PayMoneyBalanceResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyUseRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyUseResponse;
import com.wink.gongongu.domain.payment.entity.Payment;
import com.wink.gongongu.domain.payment.entity.PaymentStatus;
import com.wink.gongongu.domain.payment.exception.PaymentErrorCode;
import com.wink.gongongu.domain.payment.mapper.PaymentMapper;
import com.wink.gongongu.domain.payment.repository.PaymentRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.service.UserService;
import com.wink.gongongu.global.exception.BusinessException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final TossPaymentClient tossPaymentClient;

    @Transactional
    public PayMoneyChargeReadyResponse readyCharge(Long userId, PayMoneyChargeReadyRequest request) {
        if (request == null || request.amount() == null || request.amount() <= 0) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }

        Payment payment = Payment.builder()
            .userId(userId)
            .orderId(generateOrderId())
            .amount(request.amount())
            .status(PaymentStatus.READY)
            .build();

        Payment saved = paymentRepository.save(payment);
        return PaymentMapper.toReadyResponse(saved);
    }

    @Transactional
    public PayMoneyChargeConfirmResponse confirmCharge(Long userId, PayMoneyChargeConfirmRequest request) {
        validateConfirmRequest(request);

        Payment payment = paymentRepository.findByOrderIdAndUserId(request.orderId(), userId)
            .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        if (!payment.getAmount().equals(request.amount())) {
            throw new BusinessException(PaymentErrorCode.PAYMENT_AMOUNT_MISMATCH);
        }

        User user = userService.findById(userId);

        if (payment.getStatus() == PaymentStatus.SUCCESS) {
            if (payment.getPaymentKey() != null && payment.getPaymentKey().equals(request.paymentKey())) {
                return PaymentMapper.toConfirmResponse(payment, user.getPayMoney());
            }
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }

        if (payment.getStatus() != PaymentStatus.READY) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }

        tossPaymentClient.confirm(request.paymentKey(), request.orderId(), request.amount());

        payment.confirm(request.paymentKey());
        user.chargePayMoney(request.amount());

        return PaymentMapper.toConfirmResponse(payment, user.getPayMoney());
    }

    @Transactional
    public PayMoneyUseResponse usePayMoney(Long userId, PayMoneyUseRequest request) {
        if (request == null || request.postId() == null || request.amount() == null || request.amount() <= 0) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMONEY_USE_REQUEST);
        }

        User user = userService.findById(userId);
        if (user.getPayMoney() < request.amount()) {
            throw new BusinessException(PaymentErrorCode.INSUFFICIENT_PAY_MONEY);
        }

        user.usePayMoney(request.amount());
        return new PayMoneyUseResponse(request.postId(), request.amount(), user.getPayMoney());
    }

    public PayMoneyBalanceResponse getBalance(Long userId) {
        User user = userService.findById(userId);
        return new PayMoneyBalanceResponse(user.getPayMoney());
    }

    private String generateOrderId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void validateConfirmRequest(PayMoneyChargeConfirmRequest request) {
        if (request == null || request.paymentKey() == null || request.paymentKey().isBlank()
            || request.orderId() == null || request.orderId().isBlank()
            || request.amount() == null || request.amount() <= 0) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_CONFIRM_REQUEST);
        }
    }
}
