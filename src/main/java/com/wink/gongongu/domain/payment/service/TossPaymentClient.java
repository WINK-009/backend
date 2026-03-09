package com.wink.gongongu.domain.payment.service;

import com.wink.gongongu.domain.payment.exception.PaymentErrorCode;
import com.wink.gongongu.global.exception.BusinessException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class TossPaymentClient {

    private final RestClient restClient = RestClient.create();

    @Value("${toss.secret-key:}")
    private String tossSecretKey;

    @Value("${toss.confirm-url:https://api.tosspayments.com/v1/payments/confirm}")
    private String tossConfirmUrl;

    public void confirm(String paymentKey, String orderId, int amount) {
        if (tossSecretKey == null || tossSecretKey.isBlank()) {
            throw new BusinessException(PaymentErrorCode.TOSS_SECRET_KEY_NOT_CONFIGURED);
        }

        String encodedAuth = Base64.getEncoder()
            .encodeToString((tossSecretKey + ":").getBytes(StandardCharsets.UTF_8));

        try {
            restClient.post()
                .uri(tossConfirmUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                .body(Map.of(
                    "paymentKey", paymentKey,
                    "orderId", orderId,
                    "amount", amount
                ))
                .retrieve()
                .toBodilessEntity();
        } catch (Exception e) {
            throw new BusinessException(PaymentErrorCode.TOSS_CONFIRM_FAILED);
        }
    }
}