package com.stock.advice.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserNotLoginException extends RuntimeException{
    private final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    private final String message = "로그인이 필요한 작업입니다.";
}
