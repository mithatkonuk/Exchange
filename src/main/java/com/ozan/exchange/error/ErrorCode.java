package com.ozan.exchange.error;

import lombok.Getter;

/**
 * This class provide each different module errorCodes
 */
@Getter
public class ErrorCode
{

    private int errorCode;
    private String description;

    public ErrorCode( int errorCode, String description )
    {
        this.errorCode = errorCode;
        this.description = description;
    }

    public static class EXTERNAL_SERVICE_PROVIDER
    {
        public static final ErrorCode EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND = new ErrorCode(0,
                        "External service exchange given source / target is not supported");

        public static final ErrorCode METHOD_ARGUMENT_INVALID =
                        new ErrorCode(1, "Given Argument is not valid");
    }

}
