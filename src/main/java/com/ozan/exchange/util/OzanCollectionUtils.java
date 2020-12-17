package com.ozan.exchange.util;

import java.util.Collection;

public class OzanCollectionUtils
{
    public static boolean isEmpty( Collection collection )
    {
        return collection == null || collection.isEmpty();
    }

    public static boolean isNotEmpty( Collection collection )
    {
        return !isEmpty(collection);
    }
}
