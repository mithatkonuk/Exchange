package com.ozan.exchange.util;

import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * We can also use provider which is wrapper different time api
 * <p>
 * private DateProvider dateProvider;
 */
public class OzanDateUtils
{
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    // we can customize this with another Date formatter or create custom implementation
    public static final DateFormat YYYY_MM_DD = new SimpleDateFormat(DATE_PATTERN);

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
            return OzanDateUtils.stripTime((pattern.parse(date)));
        }
        catch( ParseException e )
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_FORMAT_PROBLEM);
        }
    }

    public static Date parse( String date, DateFormat format, boolean lenient )
                    throws ParseException
    {
        format.setLenient(lenient);
        return format.parse(date);
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
