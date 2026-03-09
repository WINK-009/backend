package com.wink.gongongu.domain.payment.exception;

import com.wink.gongongu.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Payment not found."),
    INVALID_PAYMENT_AMOUNT(HttpStatus.BAD_REQUEST, "Invalid payment amount."),
    INVALID_PAYMENT_STATUS(HttpStatus.BAD_REQUEST, "Invalid payment status transition."),
    PAYMENT_AMOUNT_MISMATCH(HttpStatus.BAD_REQUEST, "Payment amount does not match."),
    INVALID_PAYMENT_CONFIRM_REQUEST(HttpStatus.BAD_REQUEST, "Invalid payment confirm request."),
    TOSS_CONFIRM_FAILED(HttpStatus.BAD_GATEWAY, "Failed to confirm payment with Toss.");

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
