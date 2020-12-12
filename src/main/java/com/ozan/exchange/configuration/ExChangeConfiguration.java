package com.ozan.exchange.configuration;

import com.ozan.exchange.provider.RestTemplateInternalExchangeProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Extendable for configuration Exchange application
 */
@Configuration
@ComponentScan( basePackages = "com.ozan.exchange" )
@Import( { ForgienExchangeProviderConfiguration.class, FeignClientsConfiguration.class } )
public class ExChangeConfiguration
{

    @Value( "${forgien_exchange_providers.internal.timeout.connect}" )
    private int connectTimeOut;

    @Value( "${forgien_exchange_providers.internal.timeout.read}" )
    private int readTimeOut;

    @ConditionalOnProperty( prefix = "forgien_exchange_providers.internal", name = "enabled", havingValue = "true" )
    @Bean
    public RestTemplate restTemplate( RestTemplateBuilder builder )
    {
        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(getClientHttpRequestFactory());
        return restTemplate;
    }


    @ConditionalOnProperty( prefix = "forgien_exchange_providers.internal", name = "enabled", havingValue = "true" )
    @Bean
    @Qualifier( "${forgien_exchange_providers.internal.name}" )
    RestTemplateInternalExchangeProvider restTemplateInternalExchangeProvider(
                    RestTemplate restTemplate )
    {
        return new RestTemplateInternalExchangeProvider(restTemplate);
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
