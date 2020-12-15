package com.ozan.exchange.configuration;

import com.ozan.exchange.rateProvider.RateApiExternalExchangeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Extendable for service provider configuration
 */
@Configuration
public class OzanExchangeServiceProviderConfiguration extends CachingConfigurerSupport
{
    @Autowired
    private ForgienExternalExChangeConfiguration forgienExternalExChangeConfiguration;

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
}
