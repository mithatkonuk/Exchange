package com.ozan.exchange.util;

import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.exception.ExchangeServiceParamException;

import java.util.UUID;

public class AssertUtils
{

    public static void assertTransaction( final String uuid )
    {
        try
        {
            UUID.fromString(uuid);
        }
        catch( IllegalArgumentException e )
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_VALUE_TOO_LONG);
        }
    }

    public static void assertNotNull( final Object param )
    {
        if(null == param)
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NULL);
        }
    }

}
