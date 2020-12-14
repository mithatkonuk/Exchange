package com.ozan.exchange.rateProvider;

import com.ozan.exchange.configuration.ForgienExternalExChangeConfiguration;
import com.ozan.exchange.dto.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * you can also set external provider like this to extend Forgien Exchange Provider
 */
public class RateApiExternalExchangeProvider implements ForgienExchangeProvider
{
    private static final Logger logger =
                    LoggerFactory.getLogger(RateApiExternalExchangeProvider.class);

    private final RestTemplate restTemplate;
    private final ForgienExternalExChangeConfiguration configuration;
    private String URI;

    public RateApiExternalExchangeProvider( RestTemplate restTemplate,
                    ForgienExternalExChangeConfiguration configuration )
    {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
        this.URI = configuration.getUrl() + this.configuration.getExchangePath();
    }

    @Override
    public Exchange getExchange( String base, String symbols )
    {
        this.URI = this.URI.replace("{base}", base).replace("{symbols}", symbols);
        logger.info("Generated url : " + this.URI);
        return restTemplate.getForObject(this.URI, Exchange.class);
    }
}
