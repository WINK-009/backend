package com.wink.gongongu.domain.payment.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.payment.dto.PaymentCreateRequest;
import com.wink.gongongu.domain.payment.dto.PaymentCreateResponse;
import com.wink.gongongu.domain.payment.dto.PaymentDetailResponse;
import com.wink.gongongu.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
