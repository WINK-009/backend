package com.wink.gongongu.domain.payment.exception;

import com.wink.gongongu.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Payment not found."),
    PAYMENT_FORBIDDEN(HttpStatus.FORBIDDEN, "No permission to access this payment."),
    INVALID_PAYMENT_AMOUNT(HttpStatus.BAD_REQUEST, "Invalid payment amount."),
    INVALID_PAYMENT_STATUS(HttpStatus.BAD_REQUEST, "Invalid payment status transition."),
    INVALID_PROVIDER_TX_ID(HttpStatus.BAD_REQUEST, "Invalid provider transaction id.");

    private final HttpStatus httpStatus;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
