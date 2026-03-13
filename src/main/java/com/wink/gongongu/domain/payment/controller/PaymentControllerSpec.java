package com.wink.gongongu.domain.payment.controller;

import com.wink.gongongu.auth.dto.UserPrincipal;
import com.wink.gongongu.domain.payment.dto.PayMoneyBalanceResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyUseRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyUseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "결제", description = "페이머니 충전/사용 API")
public interface PaymentControllerSpec {

    @Operation(summary = "페이머니 충전 준비", description = "충전 주문을 생성하고 orderId를 발급합니다.")
    PayMoneyChargeReadyResponse readyCharge(UserPrincipal principal, PayMoneyChargeReadyRequest request);

    @Operation(summary = "페이머니 충전 승인", description = "토스 승인 검증 후 페이머니 잔액을 충전합니다.")
    PayMoneyChargeConfirmResponse confirmCharge(UserPrincipal principal, PayMoneyChargeConfirmRequest request);

    @Operation(summary = "페이머니 사용", description = "앱 내 결제 시 페이머니를 차감합니다.")
    PayMoneyUseResponse usePayMoney(UserPrincipal principal, PayMoneyUseRequest request);

    @Operation(summary = "페이머니 잔액 조회", description = "현재 로그인 사용자의 페이머니 잔액을 조회합니다.")
    PayMoneyBalanceResponse getBalance(UserPrincipal principal);
}