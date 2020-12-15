package com.ozan.exchange.cache.serviceprovider;

import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

public class ForgienServiceProviderCacheKeyGenerator implements KeyGenerator
{
    @Override
    public Object generate( Object o, Method method, Object... objects )
    {
        String[] params = (String[])objects;

        return params[0] + "-" + params[1];
    }
}
