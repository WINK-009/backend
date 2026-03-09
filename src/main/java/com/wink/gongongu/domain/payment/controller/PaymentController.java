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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "\uacb0\uc81c", description = "\ud398\uc774\uba38\ub2c8 \ucda9\uc804/\uc0ac\uc6a9 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/paymoney")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "\ud398\uc774\uba38\ub2c8 \ucda9\uc804 \uc900\ube44", description = "\ucda9\uc804 \uc8fc\ubb38\uc744 \uc0dd\uc131\ud558\uace0 orderId\ub97c \ubc1c\uae09\ud569\ub2c8\ub2e4.")
    @PostMapping("/charge/ready")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyChargeReadyResponse readyCharge(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody PayMoneyChargeReadyRequest request
    ) {
        return paymentService.readyCharge(principal.userId(), request);
    }

    @Operation(summary = "\ud398\uc774\uba38\ub2c8 \ucda9\uc804 \uc2b9\uc778", description = "\ud1a0\uc2a4 \uc2b9\uc778 \uac80\uc99d \ud6c4 \ud398\uc774\uba38\ub2c8 \uc794\uc561\uc744 \ucda9\uc804\ud569\ub2c8\ub2e4.")
    @PostMapping("/charge/confirm")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyChargeConfirmResponse confirmCharge(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody PayMoneyChargeConfirmRequest request
    ) {
        return paymentService.confirmCharge(principal.userId(), request);
    }

    @Operation(summary = "\ud398\uc774\uba38\ub2c8 \uc0ac\uc6a9", description = "\uc571 \ub0b4 \uacb0\uc81c \uc2dc \ud398\uc774\uba38\ub2c8\ub97c \ucc28\uac10\ud569\ub2c8\ub2e4.")
    @PostMapping("/use")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyUseResponse usePayMoney(
        @AuthenticationPrincipal UserPrincipal principal,
        @RequestBody PayMoneyUseRequest request
    ) {
        return paymentService.usePayMoney(principal.userId(), request);
    }

    @Operation(summary = "\ud398\uc774\uba38\ub2c8 \uc794\uc561 \uc870\ud68c", description = "\ud604\uc7ac \ub85c\uadf8\uc778 \uc0ac\uc6a9\uc790\uc758 \ud398\uc774\uba38\ub2c8 \uc794\uc561\uc744 \uc870\ud68c\ud569\ub2c8\ub2e4.")
    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    public PayMoneyBalanceResponse getBalance(@AuthenticationPrincipal UserPrincipal principal) {
        return paymentService.getBalance(principal.userId());
    }
}