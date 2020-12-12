package com.ozan.exchange.foreign.Exchange.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Extendable for configuration Exchange application
 */
@Configuration
public class ExChangeConfiguration
{

    @Value( "${resttemplate.connect.timeout}" )
    private int connectTimeOut;

    @Value( "${resttemplate.read.timeout}" )
    private int readTimeOut;

    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate(getClientHttpRequestFactory());
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
