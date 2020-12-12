package com.ozan.exchange.foreign.exchange;

import com.ozan.exchange.foreign.exchange.configuration.ForgienExchangeProviderConfiguration;
import com.ozan.exchange.foreign.exchange.dto.Exchange;
import com.ozan.exchange.foreign.exchange.provider.ForgienExchangeProvider;
import com.ozan.exchange.foreign.exchange.provider.RestTemplateInternalExchangeProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableFeignClients
public class ForeignExchangeApplication
{
    private static final Logger logger = LoggerFactory.getLogger(ForeignExchangeApplication.class);

    public static void main( String[] args )
    {
        SpringApplication.run(ForeignExchangeApplication.class, args);
    }

    @Component
    @AllArgsConstructor
    public class Runner implements CommandLineRunner
    {

        private final ForgienExchangeProvider exchangeProvider;

        private final ForgienExchangeProviderConfiguration configuration;

        @Override
        public void run( String... args )
        {
            Exchange exchange = exchangeProvider.getExchange("EUR", "TRY");

            logger.info("exchange " + exchange.toString());
            logger.info("configuration " + configuration.toString());

        }
    }

    @RestController
    @AllArgsConstructor
    public class Test
    {

        private final RestTemplateInternalExchangeProvider springRestTemplateExchangeProvider;

        @GetMapping( value = "/test" )
        public String test()
        {
            Exchange allExchanges = springRestTemplateExchangeProvider.getExchange("EUR", "TRY");
            logger.info(allExchanges.toString());
            return "test";
        }
    }

}
