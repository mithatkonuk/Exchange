package com.ozan.exchange.exception;

import com.ozan.exchange.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class ExchangeServiceParamException extends RuntimeException
{
    private final ErrorCode errorCode;

    public ExchangeServiceParamException( ErrorCode errorCode, String message )
    {
        super(message);
        this.errorCode = errorCode;
        this.errorCode.setDescription(message);
    }

    public ExchangeServiceParamException( ErrorCode errorCode )
    {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
