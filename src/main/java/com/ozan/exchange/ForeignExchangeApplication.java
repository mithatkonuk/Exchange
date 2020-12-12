package com.ozan.exchange;

import com.ozan.exchange.dto.Exchange;
import com.ozan.exchange.provider.ForgienExchangeProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
        @Qualifier( "${forgien_exchange_providers.internal.name}" )
        private final ForgienExchangeProvider exchangeProvider;

        @Override
        public void run( String... args )
        {
            Exchange exchange = exchangeProvider.getExchange("EUR", "TRY");

            logger.info("exchange " + exchange.toString());

        }
    }

}
