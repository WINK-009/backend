package com.wink.gongongu.domain.payment.service;

import com.wink.gongongu.domain.payment.dto.PaymentCreateRequest;
import com.wink.gongongu.domain.payment.dto.PaymentCreateResponse;
import com.wink.gongongu.domain.payment.dto.PaymentDetailResponse;
import com.wink.gongongu.domain.payment.entity.Payment;
import com.wink.gongongu.domain.payment.entity.PaymentStatus;
import com.wink.gongongu.domain.payment.exception.PaymentErrorCode;
import com.wink.gongongu.domain.payment.mapper.PaymentMapper;
import com.wink.gongongu.domain.payment.repository.PaymentRepository;
import com.wink.gongongu.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentCreateResponse createPayment(Long userId, PaymentCreateRequest request) {
        validateCreateRequest(request);

        Payment payment = Payment.builder()
            .userId(userId)
            .postId(request.postId())
            .amount(request.amount())
            .method(request.method())
            .status(PaymentStatus.READY)
            .build();

        Payment saved = paymentRepository.save(payment);
        return PaymentMapper.toCreateResponse(saved);
    }

    public PaymentDetailResponse getPaymentDetail(Long paymentId, Long userId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        if (!payment.getUserId().equals(userId)) {
            throw new BusinessException(PaymentErrorCode.PAYMENT_FORBIDDEN);
        }

        return PaymentMapper.toDetailResponse(payment);
    }

    private void validateCreateRequest(PaymentCreateRequest request) {
        if (request == null || request.postId() == null || request.amount() == null
            || request.amount() <= 0 || request.method() == null) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }
}
