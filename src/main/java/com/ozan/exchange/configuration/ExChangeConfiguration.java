package com.ozan.exchange.configuration;

import com.ozan.exchange.provider.RateApiExternalExchangeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Extendable for configuration Exchange application
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan( basePackages = "com.ozan.exchange" )
@Import( ForgienExternalExChangeConfiguration.class )
public class ExChangeConfiguration
{

    @Value( "${forgien_exchange_providers.external.connect}" )
    private int connectTimeOut;

    @Value( "${forgien_exchange_providers.external.read}" )
    private int readTimeOut;

    @Autowired
    private ForgienExternalExChangeConfiguration forgienExternalExChangeConfiguration;

    /*
        if we disable feign so this will be primary
     */
    @ConditionalOnProperty( prefix = "forgien_exchange_providers.external", name = "enabled", havingValue = "true" )
    @Bean
    @Qualifier( "${forgien_exchange_providers.external.name}" )
    public RateApiExternalExchangeProvider rateApiInternalExchangeProvider(
                    RestTemplateBuilder builder )
    {

        RestTemplate restTemplate = builder.build();
        restTemplate.setRequestFactory(getClientHttpRequestFactory());
        return new RateApiExternalExchangeProvider(restTemplate, forgienExternalExChangeConfiguration);
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
