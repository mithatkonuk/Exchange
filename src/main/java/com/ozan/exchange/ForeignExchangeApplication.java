package com.ozan.exchange;

import com.ozan.exchange.configuration.ForgienExchangeProviderConfiguration;
import com.ozan.exchange.dto.Exchange;
import com.ozan.exchange.provider.ForgienExchangeProvider;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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
        @Qualifier( "restTemplateInternalExchangeProvider" )
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

    //    @RestController
    //    @AllArgsConstructor
    //    public class Test
    //    {
    //        @Qualifier( "rateApiExternalExchangeProvider" )
    //        private final ForgienExchangeProvider forgienExchangeProvider;
    //
    //        @GetMapping( value = "/test" )
    //        public String test()
    //        {
    //            Exchange allExchanges = forgienExchangeProvider.getExchange("EUR", "TRY");
    //            logger.info(allExchanges.toString());
    //            return "test";
    //        }
    //    }

}
