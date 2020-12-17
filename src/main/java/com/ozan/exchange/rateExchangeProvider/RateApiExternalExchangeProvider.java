package com.ozan.exchange.rateExchangeProvider;

import com.ozan.exchange.configuration.OzanRateProviderRestConfiguration;
import com.ozan.exchange.dto.ExternalExchange;
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
    private final OzanRateProviderRestConfiguration configuration;
    private String URI;

    public RateApiExternalExchangeProvider( RestTemplate restTemplate,
                    OzanRateProviderRestConfiguration configuration )
    {
        this.restTemplate = restTemplate;
        this.configuration = configuration;
        this.URI = configuration.getUrl() + this.configuration.getExchangePath();
    }

    @Override
    public ExternalExchange getExchange( String base, String symbols )
    {
        this.URI = this.URI.replace("{base}", base).replace("{symbols}", symbols);
        logger.info("[external-service (" + configuration.getName() + ")]" + " - Generated url : " +
                        this.URI);
        ExternalExchange externalExchange = restTemplate.getForObject(this.URI, ExternalExchange.class);
        logger.info("[external-service-response] - " + externalExchange.toString());
        return externalExchange;
    }
}
