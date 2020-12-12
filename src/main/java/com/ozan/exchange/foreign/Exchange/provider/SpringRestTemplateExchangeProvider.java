package com.ozan.exchange.foreign.Exchange.provider;

import com.ozan.exchange.foreign.Exchange.dto.Exchange;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class SpringRestTemplateExchangeProvider implements ForgienExchangeProvider
{

    private final RestTemplate restTemplate;
    private final String url;
    private final String exchange;

    @Override
    public Exchange getExchange( String base, String symbols )
    {
        return null;
    }
}
