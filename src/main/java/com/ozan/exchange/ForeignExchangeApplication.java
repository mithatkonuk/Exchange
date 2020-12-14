package com.ozan.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ForeignExchangeApplication
{
    private static final Logger logger = LoggerFactory.getLogger(ForeignExchangeApplication.class);

    public static void main( String[] args )
    {
        SpringApplication.run(ForeignExchangeApplication.class, args);
    }

    //    @Component
    //    public static class Runner implements CommandLineRunner
    //    {
    //
    //        private static final Logger logger = LoggerFactory.getLogger("defaultErrorFileLogger");
    //        @Autowired
    //        @Qualifier( "${forgien_exchange_providers.feign.name}" )
    //        private ForgienExchangeProvider defaultProvider;
    //
    //        @Autowired
    //        @Qualifier( "${forgien_exchange_providers.rest.name}" )
    //        private ForgienExchangeProvider forgienExchangeProvider;
    //
    //        @Autowired
    //        private ExchangeConversionRepo exchangeConversionRepo;
    //
    //        //        AllArgsConstructor is not capability to know which qualifier
    //        //        @Autowired
    //        //        public Runner( @Qualifier( "${forgien_exchange_providers.external.name}" ) ForgienExchangeProvider externalProvider) {
    //        //
    //        //        }
    //
    //        @Override
    //        public void run( String... args )
    //        {
    //            Exchange exchange = defaultProvider.getExchange("EUR", "TRY");
    //
    //            Exchange exchange2 = forgienExchangeProvider.getExchange("EUR", "TRY");
    //
    //            ExchangeConversion exchangeConversion = new ExchangeConversion();
    //            exchangeConversion.setBase("EUR");
    //            exchangeConversion.setSymbol("TRY");
    //            exchangeConversion.setTransaction(UUID.randomUUID());
    //            exchangeConversion.setRange(exchange.getRates().get("TRY"));
    //            exchangeConversion.setAmount(10.0);
    //            exchangeConversion.setConversion(10.0 * exchangeConversion.getRange());
    //
    //            exchangeConversionRepo.save(exchangeConversion);
    //
    //            logger.error("exchange " + exchange.toString());
    //            logger.error("exchange2 " + exchange2.toString());
    //        }
    //    }
}
