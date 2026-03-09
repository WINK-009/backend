package com.wink.gongongu.domain.payment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmRequest;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeConfirmResponse;
import com.wink.gongongu.domain.payment.dto.PayMoneyChargeReadyRequest;
import com.wink.gongongu.domain.payment.entity.Payment;
import com.wink.gongongu.domain.payment.entity.PaymentStatus;
import com.wink.gongongu.domain.payment.exception.PaymentErrorCode;
import com.wink.gongongu.domain.payment.repository.PaymentRepository;
import com.wink.gongongu.domain.user.entity.User;
import com.wink.gongongu.domain.user.entity.UserType;
import com.wink.gongongu.domain.user.service.UserService;
import com.wink.gongongu.global.exception.BusinessException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private UserService userService;

    @Mock
    private TossPaymentClient tossPaymentClient;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void confirmCharge_isIdempotent_whenAlreadySuccessWithSamePaymentKey() {
        Long userId = 1L;
        String orderId = "order-1";
        String paymentKey = "payment-key-1";
        int amount = 5000;

        Payment payment = Payment.builder()
            .userId(userId)
            .orderId(orderId)
            .amount(amount)
            .status(PaymentStatus.READY)
            .build();

        User user = User.builder()
            .nickname("tester")
            .kakaoId("kakao-1")
            .userType(UserType.INDIVIDUAL)
            .region("seoul")
            .build();

        when(paymentRepository.findByOrderIdAndUserId(orderId, userId)).thenReturn(Optional.of(payment));
        when(userService.findById(userId)).thenReturn(user);

        PayMoneyChargeConfirmRequest request = new PayMoneyChargeConfirmRequest(paymentKey, orderId, amount);

        PayMoneyChargeConfirmResponse first = paymentService.confirmCharge(userId, request);
        PayMoneyChargeConfirmResponse second = paymentService.confirmCharge(userId, request);

        assertThat(first.status()).isEqualTo("SUCCESS");
        assertThat(second.status()).isEqualTo("SUCCESS");
        assertThat(first.currentPayMoney()).isEqualTo(amount);
        assertThat(second.currentPayMoney()).isEqualTo(amount);

        verify(tossPaymentClient, times(1)).confirm(eq(paymentKey), eq(orderId), eq(amount));
    }

    @Test
    void readyCharge_throws_whenAmountIsInvalid() {
        Long userId = 1L;
        PayMoneyChargeReadyRequest request = new PayMoneyChargeReadyRequest(0);

        assertThatThrownBy(() -> paymentService.readyCharge(userId, request))
            .isInstanceOf(BusinessException.class)
            .extracting(ex -> ((BusinessException) ex).getErrorCode())
            .isEqualTo(PaymentErrorCode.INVALID_PAYMENT_AMOUNT);

        verify(paymentRepository, times(0)).save(any());
    }
}
