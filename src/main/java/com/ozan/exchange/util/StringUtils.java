package com.ozan.exchange.util;

public class StringUtils
{

    public static boolean isEmpty( String[] arr )
    {
        return (arr == null || arr.length == 0);
    }

    public static boolean isNotEmpty( String[] arr )
    {
        return !isEmpty(arr);
    }
}
