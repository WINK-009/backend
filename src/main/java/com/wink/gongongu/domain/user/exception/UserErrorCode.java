package com.wink.gongongu.domain.user.exception;

import com.wink.gongongu.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {

    USER_NOT_FOUND("해당 유저를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_ALREADY_SIGNED_UP("이미 회원가입이 완료된 유저입니다.", HttpStatus.BAD_REQUEST),
    INVALID_USER_TYPE("유효하지 않은 유저 타입입니다. 개인형과 기업형 중 선택해주세요.", HttpStatus.BAD_REQUEST),
    IMAGE_AND_NICKNAME_EMPTY("수정할 닉네임 또는 이미지가 필요합니다.",HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
