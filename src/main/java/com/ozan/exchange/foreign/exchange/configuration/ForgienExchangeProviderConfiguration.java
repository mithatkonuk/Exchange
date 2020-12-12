package com.ozan.exchange.foreign.exchange.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties( prefix = "forgien-exchange-providers" )
@Data
public class ForgienExchangeProviderConfiguration
{
    private String name;
    private String url;
    private String exchangePath;

}
