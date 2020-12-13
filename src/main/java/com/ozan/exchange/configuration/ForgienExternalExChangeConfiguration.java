package com.ozan.exchange.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties( prefix = "forgien-exchange-providers.external" )
public class ForgienExternalExChangeConfiguration
{

    private boolean enable;
    private long read;
    private long connect;
    private String name;
    private String url;
    private String exchangePath;
}
