package com.ozan.exchange.exception.error;

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
        public static final ErrorCode EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND = new ErrorCode(1,
                        "External service exchange given source / target is not supported");

        public static final ErrorCode METHOD_ARGUMENT_INVALID =
                        new ErrorCode(2, "Given Argument is not valid");
    }

    public static class EXCHANGE_SERVICE
    {
        public static final ErrorCode ILLEGAL_ARGUMENT_VALUE_TOO_LONG =
                        new ErrorCode(3, "Given Parameter value too long");

        public static final ErrorCode ILLEGAL_ARGUMENT_NULL =
                        new ErrorCode(4, "Given Parameter must not be null");

        public static final ErrorCode ILLEGAL_ARGUMENT_NOT_ACCEPTABLE =
                        new ErrorCode(5, "Given Parameter is not acceptable");

        public static final ErrorCode ILLEGAL_ARGUMENT_FORMAT_PROBLEM =
                        new ErrorCode(6, "Given Parameter format problem exist");

        public static final ErrorCode NOT_FOUND = new ErrorCode(7, "Requested data is not found");
    }

    public static class GENERIC
    {
        public static final ErrorCode GENERIC_ERROR = new ErrorCode(8, "Generic Error occured");

    }

}
