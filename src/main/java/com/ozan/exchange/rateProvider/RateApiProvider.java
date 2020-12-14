package com.ozan.exchange.rateProvider;

import com.ozan.exchange.dto.Exchange;
import com.ozan.exchange.exception.ExternalServiceException;
import com.ozan.exchange.exception.error.ErrorCode;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component( "rateApiProvider" )
@AllArgsConstructor
public class RateApiProvider implements ForgienExchangeProvider
{
    @Qualifier( "${forgien_exchange_providers.feign.name}" )
    @Autowired
    private ForgienExchangeProvider forgienExchangeProvider;

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
}
