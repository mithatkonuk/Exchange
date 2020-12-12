package com.ozan.exchange.provider;

import com.ozan.exchange.dto.Exchange;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

/**
 * you can also set internal provider and also customize with other external service
 */
@AllArgsConstructor
public class RateApiInternalExchangeProvider implements ForgienExchangeProvider
{
    private final RestTemplate restTemplate;

    @Override
    public Exchange getExchange( String base, String symbols )
    {
        return restTemplate.getForObject("https://api.ratesapi.io/api/latest",Exchange.class);
    }
}
