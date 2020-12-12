package com.ozan.exchange.foreign.exchange.configuration;

import com.ozan.exchange.foreign.exchange.provider.RestTemplateInternalExchangeProvider;
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
@ComponentScan( basePackages = "com.ozan.exchange.foreign.exchange" )
@Import( ForgienExchangeProviderConfiguration.class )
public class ExChangeConfiguration
{

    @Value( "${provider.timeout.connect}" )
    private int connectTimeOut;

    @Value( "${provider.timeout.read}" )
    private int readTimeOut;

    @ConditionalOnProperty( prefix = "provider.internal", name = "enabled", havingValue = "true" )
    @Bean
    public RestTemplate restTemplate( RestTemplateBuilder builder )
    {
        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(getClientHttpRequestFactory());
        return restTemplate;
    }

    @ConditionalOnProperty( prefix = "provider.internal", name = "enabled", havingValue = "true" )
    @Bean
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
