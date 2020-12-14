package com.ozan.exchange.util;

import java.util.UUID;

public class StringUtils
{
    private static final String WHITE_SPACE_REGEX = ".*\\w.*";

    public static boolean isEmpty( String[] arr )
    {
        return (arr == null || arr.length == 0);
    }

    public static boolean isNotBlank( String[] arr )
    {
        if(null == arr)
        {
            return false;
        }

        for( String elements : arr )
        {
            if(!elements.matches(WHITE_SPACE_REGEX))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean isNotEmpty( String[] arr )
    {
        return !isEmpty(arr) && isNotBlank(arr);
    }

    public static UUID generateUUID()
    {
        return UUID.randomUUID();
    }
}
