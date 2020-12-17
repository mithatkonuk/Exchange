package com.ozan.exchange.exception.error;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * This class provide each different module errorCodes
 */
@Getter
@Setter
public class ErrorCode implements Serializable
{

    public static final ErrorCode NOT_ERROR = new ErrorCode(0, null);
    private int code;
    private String description;

    public ErrorCode( int errorCode, String description )
    {
        this.code = errorCode;
        this.description = description;
    }

    public ErrorCode()
    {

    }

    public static class EXTERNAL_SERVICE_PROVIDER
    {
        public static final ErrorCode EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND = new ErrorCode(1,
                        "Given Exchange source / Given Exchange target is not supported");

        public static final ErrorCode METHOD_ARGUMENT_INVALID =
                        new ErrorCode(2, "Given Argument is not valid");
    }

    public static class EXCHANGE_SERVICE
    {

        public static final ErrorCode ILLEGAL_ARGUMENT_NULL =
                        new ErrorCode(3, "Given Parameter must not be null");

        public static final ErrorCode ILLEGAL_ARGUMENT_NOT_ACCEPTABLE =
                        new ErrorCode(4, "Given Parameter is not acceptable");

        public static final ErrorCode ILLEGAL_ARGUMENT_FORMAT_PROBLEM = new ErrorCode(5,
                        "Format problem exist on given Parameter ,date format [YYYY-MM-DD]");

        public static final ErrorCode NOT_FOUND = new ErrorCode(6, "Requested data is not found");
    }

    public static class GENERIC
    {
        public static final ErrorCode GENERIC_ERROR = new ErrorCode(7, "Generic Error occured");

    }

}
