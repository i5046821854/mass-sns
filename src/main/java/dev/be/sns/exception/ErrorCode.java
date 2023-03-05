package dev.be.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    DUPLICATED_USER_NAME(HttpStatus.CONFLICT, "Duplicated ID"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User Not Found"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "PassWord is Invalid"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "Token is Invalid"),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "Permission is not valid"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not found")
    ;


    private HttpStatus status;
    private String message;

}
