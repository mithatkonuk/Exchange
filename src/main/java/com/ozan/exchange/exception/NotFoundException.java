package com.ozan.exchange.exception;

import com.ozan.exchange.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException
{
    private ErrorCode errorCode;

    public NotFoundException( ErrorCode errorCode, String message )
    {
        super(message);
        this.errorCode = errorCode;
    }

    public NotFoundException( ErrorCode errorCode )
    {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
