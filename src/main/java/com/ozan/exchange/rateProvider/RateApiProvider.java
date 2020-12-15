package com.ozan.exchange.rateProvider;

import com.ozan.exchange.dto.Exchange;
import com.ozan.exchange.exception.ExternalServiceException;
import com.ozan.exchange.exception.error.ErrorCode;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component( "rateApiProvider" )
@AllArgsConstructor
public class RateApiProvider implements ForgienExchangeProvider
{

    private static final Logger logger = LoggerFactory.getLogger(RateApiProvider.class);
    @Qualifier( "${forgien_exchange_providers.feign.name}" )
    @Autowired
    private ForgienExchangeProvider forgienExchangeProvider;

    @Cacheable( value = "${forgien.service.provider.request.cache.name}", keyGenerator = "forgienCacheKeyGenerator" )
    @Override
    public Exchange getExchange( String base, String symbols )
    {
        try
        {
            return forgienExchangeProvider.getExchange(base, symbols);
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
