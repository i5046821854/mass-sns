package dev.be.sns.exception;

import dev.be.sns.SnsApplication;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SnsApplicationException extends RuntimeException{

    private ErrorCode errorCode;
    private String message;

    public SnsApplicationException(ErrorCode e)
    {
        this.errorCode = e;
        this.message = null;
    }

    @Override
    public String getMessage() {
        if(message == null)
            return errorCode.getMessage();
        return String.format("%s. %s", errorCode.getMessage(), message);
    }
}
