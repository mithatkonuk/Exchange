package com.ozan.exchange.util;

import com.ozan.exchange.exception.NotFoundException;
import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;

import java.text.ParseException;
import java.util.Collection;
import java.util.UUID;

/**
 * Assert Utilities
 *
 * @author mithat.konuk
 */
public class OzanAssertUtils
{

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
        if(OzanStringUtils.isEmpty(word))
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NULL);
        }

        if(OzanStringUtils.isBlank(word))
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NOT_ACCEPTABLE);
        }
    }

    public static void assertCollectionIsEmpty( Collection collection, ErrorCode errorCode )
    {
        if(OzanCollectionUtils.isEmpty(collection))
        {
            throw new NotFoundException(errorCode);
        }
    }

    public static void assertDateFormat( String date )
    {
        try
        {
            OzanDateUtils.parse(date, OzanDateUtils.YYYY_MM_DD, false);

        }
        catch( IllegalArgumentException | ParseException e )
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_FORMAT_PROBLEM);
        }

    }

}
