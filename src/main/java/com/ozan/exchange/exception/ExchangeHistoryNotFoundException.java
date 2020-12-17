package com.ozan.exchange.exception;

import com.ozan.exchange.exception.error.ErrorCode;
import lombok.Getter;

@Getter
public class ExchangeHistoryNotFoundException extends RuntimeException
{
    private ErrorCode errorCode;

    public ExchangeHistoryNotFoundException( ErrorCode errorCode, String message )
    {
        super(message);
        this.errorCode = errorCode;
    }

    public ExchangeHistoryNotFoundException( ErrorCode errorCode )
    {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
