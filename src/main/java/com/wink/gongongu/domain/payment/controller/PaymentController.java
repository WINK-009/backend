package com.wink.gongongu.domain.payment.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.payment.dto.PayMoneyBalanceResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyUseRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyUseResponse;
import com.wink.gongongu.domain.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paymoney")
public class PaymentController implements PaymentControllerSpec {

    private final PaymentService paymentService;

    @Override
    @PostMapping("/charge/ready")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyChargeReadyResponse readyCharge(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody PayMoneyChargeReadyRequest request
    ) {
        return paymentService.readyCharge(principal.userId(), request);
    }

    @Override
    @PostMapping("/charge/confirm")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyChargeConfirmResponse confirmCharge(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody PayMoneyChargeConfirmRequest request
    ) {
        return paymentService.confirmCharge(principal.userId(), request);
    }

    @Override
    @PostMapping("/use")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyUseResponse usePayMoney(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody PayMoneyUseRequest request
    ) {
        return paymentService.usePayMoney(principal.userId(), request);
    }

    @Override
    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyBalanceResponse getBalance(@AuthenticationPrincipal UserPrincipal principal) {
        return paymentService.getBalance(principal.userId());
    }
}
