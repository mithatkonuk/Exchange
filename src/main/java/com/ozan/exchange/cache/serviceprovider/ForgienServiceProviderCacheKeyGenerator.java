package com.ozan.exchange.cache.serviceprovider;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * Cache key generator
 * please be sure have two arguments when cache used,
 * we can also handle one parameter method for caching
 * @author mithat.konuk
 */
public class ForgienServiceProviderCacheKeyGenerator implements KeyGenerator
{
    @Override
    public Object generate( Object o, Method method, Object... objects )
    {
        String[] params = (String[])objects;

        return params[0] + "-" + params[1];
    }
}
