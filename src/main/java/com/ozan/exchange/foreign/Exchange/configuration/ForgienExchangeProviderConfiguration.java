package com.ozan.exchange.foreign.Exchange.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties( prefix = "forgien-exchange-providers" )
@Setter
@Getter
@Data
public class ForgienExchangeProviderConfiguration
{
    private String name;
    private String url;
    private String exchangePath;

}
