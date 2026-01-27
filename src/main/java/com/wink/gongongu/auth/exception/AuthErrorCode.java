package com.wink.gongongu.auth.exception;

import com.wink.gongongu.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    ACCESS_TOKEN_EXPIRED("만료된 액세스 토큰입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS_TOKEN("유효하지 않은 액세스 토큰입니다.", HttpStatus.UNAUTHORIZED),
    MISSING_ACCESS_TOKEN("액세스 토큰이 존재하지 않습니다.", HttpStatus.UNAUTHORIZED),
    UNKNOWN_ERROR("알 수 없는 오류가 발생했습니다.", HttpStatus.UNAUTHORIZED);

    private final String message;
    private final HttpStatus status;
}
