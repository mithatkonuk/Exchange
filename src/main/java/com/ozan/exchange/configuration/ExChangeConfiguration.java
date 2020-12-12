package com.ozan.exchange.configuration;

import com.ozan.exchange.provider.RateApiInternalExchangeProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Extendable for configuration Exchange application
 */
@Configuration
@ComponentScan( basePackages = "com.ozan.exchange" )
@Import( { ForgienExchangeProviderConfiguration.class } )
public class ExChangeConfiguration
{

    @Value( "${forgien_exchange_providers.internal.timeout.connect}" )
    private int connectTimeOut;

    @Value( "${forgien_exchange_providers.internal.timeout.read}" )
    private int readTimeOut;

    /*
        if we disable feign so this will be primary
     */
    @ConditionalOnProperty( prefix = "forgien_exchange_providers.internal", name = "enabled", havingValue = "true" )
    @Bean
    @Qualifier( "${forgien_exchange_providers.internal.name}" )
    RateApiInternalExchangeProvider restTemplateInternalExchangeProvider(
                    RestTemplateBuilder builder )
    {

        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(getClientHttpRequestFactory());
        return new RateApiInternalExchangeProvider(restTemplate);
    }

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory()
    {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
                        new HttpComponentsClientHttpRequestFactory();
        //Connect timeout
        clientHttpRequestFactory.setConnectTimeout(connectTimeOut);

        //Read timeout
        clientHttpRequestFactory.setReadTimeout(readTimeOut);
        return clientHttpRequestFactory;
    }
}
