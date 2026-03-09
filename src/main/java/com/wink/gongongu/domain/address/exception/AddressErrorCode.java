package com.wink.gongongu.domain.address.exception;

import com.wink.gongongu.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AddressErrorCode implements ErrorCode {

    ADDRESS_NOT_FOUND("해당 주소를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String message;
    private final HttpStatus status;
}
