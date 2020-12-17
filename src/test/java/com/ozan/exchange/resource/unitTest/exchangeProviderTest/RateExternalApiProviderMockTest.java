package com.ozan.exchange.resource.unitTest.exchangeProviderTest;

import com.ozan.exchange.dto.ExternalExchange;
import com.ozan.exchange.rateExchangeProvider.RateApiExternalExchangeProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith( SpringExtension.class )
@SpringBootTest
public class RateExternalApiProviderMockTest
{
    private RateApiExternalExchangeProvider rateExchangeApiProvider =
                    Mockito.mock(RateApiExternalExchangeProvider.class);

    @Before
    public void beforeSet()
    {

        Map<String, Double> rates = new HashMap<>();
        rates.put("TRY", 9.52);

        when(this.rateExchangeApiProvider.getExchange(any(), any()))
                        .thenReturn(ExternalExchange.builder().base("EUR").rates(rates).build());
    }

    @Test
    public void check_rate_external_service_provider_is_not_null()
    {
        Assertions.assertNotNull(rateExchangeApiProvider);
    }

    @Test
    public void check_exchange_rate()
    {
        ExternalExchange externalExchange = this.rateExchangeApiProvider.getExchange("EUR", "TRY");

        Assertions.assertEquals(externalExchange.getBase(), "EUR");
        Assertions.assertEquals(externalExchange.getRates().size(), 1);
        Assertions.assertEquals(externalExchange.getRates().get("TRY"), 9.52);
    }
}
