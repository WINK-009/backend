package com.wink.gongongu.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum S3ErrorCode implements ErrorCode {

    CANNOT_UPLOAD_EMPTY_FILE("빈 파일은 업로드할 수 없습니다.", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_IMAGE_TYPE("지원하지 않는 이미지 형식입니다.", HttpStatus.BAD_REQUEST),
    FILE_EXTENSION_NOT_FOUND("파일 확장자를 확인할 수 없습니다.",HttpStatus.BAD_REQUEST);

    private final String message;
    private final HttpStatus status;
}
