package com.wink.gongongu.domain.payment.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.payment.dto.PaymentCreateRequest;
import com.wink.gongongu.domain.payment.dto.PaymentCreateResponse;
import com.wink.gongongu.domain.payment.dto.PaymentDetailResponse;
import com.wink.gongongu.domain.payment.dto.PaymentListResponse;
import com.wink.gongongu.domain.payment.dto.PaymentSuccessCallbackRequest;
import com.wink.gongongu.domain.payment.entity.PaymentStatus;
import com.wink.gongongu.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentCreateResponse createPayment(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody PaymentCreateRequest request
    ) {
        return paymentService.createPayment(principal.userId(), request);
    }

    @GetMapping("/{paymentId}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDetailResponse getPaymentDetail(
        @PathVariable Long paymentId,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        return paymentService.getPaymentDetail(paymentId, principal.userId());
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public PaymentListResponse getMyPayments(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestParam(required = false) PaymentStatus status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size
    ) {
        return paymentService.getMyPayments(principal.userId(), status, page, size);
    }

    @PatchMapping("/{paymentId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDetailResponse cancelPayment(
        @PathVariable Long paymentId,
        @AuthenticationPrincipal UserPrincipal principal
    ) {
        return paymentService.cancelPayment(paymentId, principal.userId());
    }

    @PostMapping("/callbacks/{paymentId}/success")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDetailResponse handleSuccessCallback(
        @PathVariable Long paymentId,
        @RequestBody PaymentSuccessCallbackRequest request
    ) {
        return paymentService.markPaymentSuccess(paymentId, request);
    }

    @PostMapping("/callbacks/{paymentId}/fail")
    @ResponseStatus(HttpStatus.OK)
    public PaymentDetailResponse handleFailCallback(@PathVariable Long paymentId) {
        return paymentService.markPaymentFailed(paymentId);
    }
}
