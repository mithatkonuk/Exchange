package com.ozan.exchange.configuration;

import com.ozan.exchange.cache.serviceprovider.ForgienServiceProviderCacheKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class OzanExchangeCacheConfiguration
{

    @Value( "${forgien.service.provider.request.cache.name}" )
    private String serviceProviderCacheName;

    @Bean
    public CacheManager cacheManager()
    {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        Cache ServiceProviderRequestCache = new ConcurrentMapCache(serviceProviderCacheName);
        cacheManager.setCaches(Arrays.asList(ServiceProviderRequestCache));
        return cacheManager;
    }

    @Bean( "forgienCacheKeyGenerator" )
    public KeyGenerator keyGenerator()
    {
        return new ForgienServiceProviderCacheKeyGenerator();
    }
}
