package com.ozan.exchange.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Extendable main application configuration
 */
@Configuration
@EnableScheduling
@EnableAspectJAutoProxy
@ComponentScan( basePackages = "com.ozan.exchange" )
@Import( { ForgienExternalExChangeConfiguration.class,
                OzanExchangeServiceProviderConfiguration.class, OzanCacheConfiguration.class,
                SwaggerConfiguration.class } )
public class OzanExchangeConfiguration
{
}
