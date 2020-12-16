package com.ozan.exchange.util;

import java.util.UUID;

public class StringUtils
{
    public static final int PAIR_LENGHT = 2;
    private static final String WHITE_SPACE_REGEX = ".*\\w.*";
    private static final String EMPTY_STRING_REGEX = "^$";

    public static boolean isEmpty( String[] arr )
    {
        return (arr == null || arr.length == 0);
    }

    public static boolean isEmpty( String word )
    {
        return word == null || word.matches(EMPTY_STRING_REGEX);
    }

    public static boolean isNotEmpty( String word )
    {
        return !isEmpty(word);
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

    public static String[] split( String word, String regex )
    {
        return word.split(regex);
    }

    public static boolean isBlank( String word )
    {
        return isNotEmpty(word) && word.trim().length() == 0;
    }

    public static boolean isNotBlank( String word )
    {
        return isNotEmpty(word) && !isBlank(word);
    }
}
