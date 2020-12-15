package com.ozan.exchange.configuration;

import com.ozan.exchange.cache.serviceprovider.ForgienServiceProviderCacheKeyGenerator;
import com.ozan.exchange.rateProvider.RateApiExternalExchangeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Extendable for configuration Exchange application
 */
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
@ComponentScan( basePackages = "com.ozan.exchange" )
@Import( ForgienExternalExChangeConfiguration.class )
public class ExChangeConfiguration extends CachingConfigurerSupport
{
    @Autowired
    private ForgienExternalExChangeConfiguration forgienExternalExChangeConfiguration;

    @Value( "${forgien.service.provider.request.cache.name}" )
    private String serviceProviderCacheName;

    /*
        secondary provider to take service from external resource
     */
    @ConditionalOnProperty( prefix = "forgien_exchange_providers.rest", name = "enabled", havingValue = "true" )
    @Bean
    @Qualifier( "${forgien_exchange_providers.rest.name}" )
    public RateApiExternalExchangeProvider rateApiInternalExchangeProvider(
                    RestTemplateBuilder builder )
    {

        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(getClientHttpRequestFactory());
        return new RateApiExternalExchangeProvider(restTemplate,
                        forgienExternalExChangeConfiguration);
    }

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory()
    {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                        new HttpComponentsClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory
                        .setConnectTimeout(forgienExternalExChangeConfiguration.getConnect());

        //Read timeout
        clientHttpRequestFactory.setReadTimeout(forgienExternalExChangeConfiguration.getRead());
        return clientHttpRequestFactory;
    }

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
