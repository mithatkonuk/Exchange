package com.ozan.exchange.util;

import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;

import java.util.Collection;
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

    public static void assertNotNullPair( final Object val, final Object val2 )
    {
        if(null == val && null == val2)
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NULL);
        }
    }

    public static void assertIsBlank( String word )
    {
        if(StringUtils.isEmpty(word))
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NULL);
        }

        if(StringUtils.isBlank(word))
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NOT_ACCEPTABLE);
        }
    }

    public static void assertCollectionIsEmpty( Collection collection, ErrorCode errorCode )
    {
        if(CollectionUtils.isEmpty(collection))
        {
            throw new ExchangeServiceParamException(errorCode);
        }
    }

}
