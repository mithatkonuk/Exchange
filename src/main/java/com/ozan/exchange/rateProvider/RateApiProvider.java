package com.ozan.exchange.rateProvider;

import com.ozan.exchange.dto.ExternalExchange;
import com.ozan.exchange.exception.ExternalServiceException;
import com.ozan.exchange.exception.error.ErrorCode;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component( "rateApiProvider" )
public class RateApiProvider implements ForgienExchangeProvider
{

    private static final Logger logger = LoggerFactory.getLogger(RateApiProvider.class);

    private ForgienExchangeProvider forgienExchangeProvider;

    @Value( "${forgien_exchange_providers.feign.name}" )
    private String providerName;

    @Autowired
    public RateApiProvider( @Qualifier( "${forgien_exchange_providers.feign.name}" )
                    ForgienExchangeProvider forgienExchangeProvider )
    {
        this.forgienExchangeProvider = forgienExchangeProvider;
    }

    @Cacheable( value = "${forgien.service.provider.request.cache.name}", keyGenerator = "forgienCacheKeyGenerator" )
    @Override
    public ExternalExchange getExchange( String base, String symbols )
    {
        try
        {
            ExternalExchange externalExchange = forgienExchangeProvider.getExchange(base, symbols);

            logger.info("[external-service (" + providerName + ")] :  " + externalExchange.toString());

            return externalExchange;
        }
        catch( FeignException e )
        {
            throw new ExternalServiceException(
                            ErrorCode.EXTERNAL_SERVICE_PROVIDER.EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND,
                            "External service is not supporting given exchange");
        }
    }

    @CacheEvict( allEntries = true, cacheNames = {
                    "${forgien.service.provider.request.cache.name}" } )
    @Scheduled( fixedDelayString = "${forgien.service.provider.request.cache.ttl}" )
    public void cacheEvict()
    {
        logger.info("Cahce is refreshed [cahce-evict]");
    }
}
