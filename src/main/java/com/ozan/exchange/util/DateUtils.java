package com.ozan.exchange.util;

import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;

import java.text.*;
import java.util.Calendar;
import java.util.Date;

/**
 * We can also use provider which is wrapper different time api
 * <p>
 * private DateProvider dateProvider;
 */
public class DateUtils
{
    // we can customize this with another Date formatter or create custom implementation
    public static final DateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");

    public static Date nowAsDate()
    {
        return new Date();
    }

    public static Long nowAsTimestamp()
    {
        return nowAsDate().getTime();
    }

    public static Date fromString( String date, DateFormat pattern )
    {
        try
        {
            return DateUtils.stripTime((pattern.parse(date)));
        }
        catch( ParseException e )
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NOT_ACCEPTABLE);
        }
    }

    public static Date stripTime( Date date )
    {
        if(date == null)
        {
            return date;
        }
        else
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(11, 0);
            cal.set(12, 0);
            cal.set(13, 0);
            cal.set(14, 0);
            return cal.getTime();
        }
    }

}
