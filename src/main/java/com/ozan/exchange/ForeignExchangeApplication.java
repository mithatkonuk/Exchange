package com.ozan.exchange;

import com.ozan.exchange.dto.Exchange;
import com.ozan.exchange.provider.ForgienExchangeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public static class Runner implements CommandLineRunner
    {

        private static final Logger logger = LoggerFactory.getLogger("defaultErrorFileLogger");
        @Autowired
        @Qualifier( "${forgien_exchange_providers.default.name}" )
        private ForgienExchangeProvider defaultProvider;

        @Autowired
        @Qualifier( "${forgien_exchange_providers.external.name}" )
        private ForgienExchangeProvider internalProvider;

        //        AllArgsConstructor is not capability to know which qualifier
        //        @Autowired
        //        public Runner( @Qualifier( "${forgien_exchange_providers.external.name}" ) ForgienExchangeProvider externalProvider) {
        //
        //        }

        @Override
        public void run( String... args )
        {
            Exchange exchange = defaultProvider.getExchange("EUR", "TRY");

            Exchange exchange2 = internalProvider.getExchange("EUR", "TRY");

            logger.error("exchange " + exchange.toString());
            logger.error("exchange2 " + exchange2.toString());
        }
    }
}
