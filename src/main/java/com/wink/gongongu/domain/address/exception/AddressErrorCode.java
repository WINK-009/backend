package com.wink.gongongu.domain.address.exception;

import com.wink.gongongu.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AddressErrorCode implements ErrorCode {

    ADDRESS_NOT_FOUND("해당 주소를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    FIRST_ADDRESS_MUST_BE_DEFAULT("첫 번째 등록 주소는 무조건 기본 주소록이여야합니다.", HttpStatus.BAD_REQUEST),
    CANNOT_DELETE_DEFAULT_ADDRESS("기본 주소록은 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST),
    DEFAULT_ADDRESS_LEAST_ONE("기본 주소록은 무조건 하나 이상 존재해야합니다.", HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
