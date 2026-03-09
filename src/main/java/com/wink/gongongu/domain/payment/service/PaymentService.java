package com.wink.gongongu.domain.payment.service;

import com.wink.gongongu.domain.payment.dto.PaymentCreateRequest;
import com.wink.gongongu.domain.payment.dto.PaymentCreateResponse;
import com.wink.gongongu.domain.payment.dto.PaymentDetailResponse;
import com.wink.gongongu.domain.payment.dto.PaymentListItemResponse;
import com.wink.gongongu.domain.payment.dto.PaymentListResponse;
import com.wink.gongongu.domain.payment.dto.PaymentSuccessCallbackRequest;
import com.wink.gongongu.domain.payment.entity.Payment;
import com.wink.gongongu.domain.payment.entity.PaymentStatus;
import com.wink.gongongu.domain.payment.exception.PaymentErrorCode;
import com.wink.gongongu.domain.payment.mapper.PaymentMapper;
import com.wink.gongongu.domain.payment.repository.PaymentRepository;
import com.wink.gongongu.global.exception.BusinessException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;

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
        Payment payment = paymentRepository.findByPaymentIdAndUserId(paymentId, userId)
            .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        return PaymentMapper.toDetailResponse(payment);
    }

    public PaymentListResponse getMyPayments(Long userId, PaymentStatus status, int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = size <= 0 ? DEFAULT_PAGE_SIZE : Math.min(size, MAX_PAGE_SIZE);
        Pageable pageable = PageRequest.of(safePage, safeSize);

        Slice<Payment> payments = status == null
            ? paymentRepository.findByUserIdOrderByPaymentIdDesc(userId, pageable)
            : paymentRepository.findByUserIdAndStatusOrderByPaymentIdDesc(userId, status, pageable);

        List<PaymentListItemResponse> items = payments.stream()
            .map(PaymentMapper::toListItemResponse)
            .toList();

        return new PaymentListResponse(items, payments.hasNext(), safePage, safeSize);
    }

    @Transactional
    public PaymentDetailResponse cancelPayment(Long paymentId, Long userId) {
        Payment payment = paymentRepository.findByPaymentIdAndUserId(paymentId, userId)
            .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        if (payment.getStatus() != PaymentStatus.READY) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }

        payment.markCanceled();
        return PaymentMapper.toDetailResponse(payment);
    }

    @Transactional
    public PaymentDetailResponse markPaymentSuccess(Long paymentId, PaymentSuccessCallbackRequest request) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        if (payment.getStatus() != PaymentStatus.READY) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }

        if (request == null || request.providerTxId() == null || request.providerTxId().isBlank()) {
            throw new BusinessException(PaymentErrorCode.INVALID_PROVIDER_TX_ID);
        }

        payment.markSuccess(request.providerTxId().trim());
        return PaymentMapper.toDetailResponse(payment);
    }

    @Transactional
    public PaymentDetailResponse markPaymentFailed(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new BusinessException(PaymentErrorCode.PAYMENT_NOT_FOUND));

        if (payment.getStatus() != PaymentStatus.READY) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_STATUS);
        }

        payment.markFailed();
        return PaymentMapper.toDetailResponse(payment);
    }

    private void validateCreateRequest(PaymentCreateRequest request) {
        if (request == null || request.postId() == null || request.amount() == null
            || request.amount() <= 0 || request.method() == null) {
            throw new BusinessException(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);
        }
    }
}
