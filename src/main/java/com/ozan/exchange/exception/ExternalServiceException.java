package com.ozan.exchange.exception;

import com.ozan.exchange.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class ExternalServiceException extends RuntimeException
{
    private final ErrorCode errorCode;

    public ExternalServiceException( ErrorCode errorCode, String message )
    {
        super(message);
        this.errorCode = errorCode;

    }
}
