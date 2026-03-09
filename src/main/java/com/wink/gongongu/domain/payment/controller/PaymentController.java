package com.wink.gongongu.domain.payment.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyResponse;
import com.wink.gongongu.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paymoney/charge")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/ready")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyChargeReadyResponse readyCharge(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody PayMoneyChargeReadyRequest request
    ) {
        return paymentService.readyCharge(principal.userId(), request);
    }

    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyChargeConfirmResponse confirmCharge(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody PayMoneyChargeConfirmRequest request
    ) {
        return paymentService.confirmCharge(principal.userId(), request);
    }
}
