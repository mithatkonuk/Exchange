package com.ozan.exchange.foreign.Exchange;

import com.ozan.exchange.foreign.Exchange.configuration.ForgienExchangeProviderConfiguration;
import com.ozan.exchange.foreign.Exchange.dto.Exchange;
import com.ozan.exchange.foreign.Exchange.provider.ForgienExchangeProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

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

}
