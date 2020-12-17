package com.ozan.exchange.resource.unitTest.exchangeProviderTest;

import com.ozan.exchange.dto.ExternalExchange;
import com.ozan.exchange.rateExchangeProvider.RateExchangeApiProvider;
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
public class RateApiProviderMockTest
{
    private RateExchangeApiProvider rateExchangeApiProvider =
                    Mockito.mock(RateExchangeApiProvider.class);

    @Before
    public void beforeSet()
    {
        Map<String, Double> rates = new HashMap<>();
        rates.put("TRY", 9.52);
        when(rateExchangeApiProvider.getExchange(any(), any()))
                        .thenReturn(ExternalExchange.builder().rates(rates).base("EUR").build());
    }

    @Test
    public void check_rate_api_provider_is_not_null()
    {
        Assertions.assertNotNull(rateExchangeApiProvider);
    }

    @Test
    public void check_eur_try_exchange_rate()
    {
        ExternalExchange externalExchange = rateExchangeApiProvider.getExchange("EUR", "TRY");
        Assertions.assertEquals(externalExchange.getBase(), "EUR");
        Assertions.assertEquals(externalExchange.getRates().get("TRY"), 9.52);
    }
}
