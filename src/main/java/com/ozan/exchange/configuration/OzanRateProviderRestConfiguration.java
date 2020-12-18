package com.ozan.exchange.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * custom configuration for rest exchange provider
 * @author mithat.konuk
 */
@Data
@Configuration
@ConfigurationProperties( prefix = "forgien-exchange-providers.rest" )
public class OzanRateProviderRestConfiguration
{

    private boolean enable;
    private int read;
    private int connect;
    private String name;
    private String url;
    private String exchangePath;
}
