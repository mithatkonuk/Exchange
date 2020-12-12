package com.ozan.exchange.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties( prefix = "forgien-exchange-providers.external" )
@Data
public class ForgienExchangeProviderConfiguration
{
    private String name;
    private String url;
}
