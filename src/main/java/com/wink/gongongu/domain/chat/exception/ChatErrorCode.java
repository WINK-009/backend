package com.wink.gongongu.domain.chat.exception;

import com.wink.gongongu.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {

    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "Chat room not found."),
    CHAT_ROOM_ALREADY_EXISTS(HttpStatus.CONFLICT, "Chat room already exists for this post."),
    INVALID_CHAT_TYPE(HttpStatus.BAD_REQUEST, "Invalid chat room type.");

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
