package com.ozan.exchange.resource.unitTest.OzanExchangeServiceTest;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import com.ozan.exchange.service.ExchangeConversionService;
import org.apache.http.client.utils.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith( SpringExtension.class )
@SpringBootTest
public class OzanExchangeServiceTest
{

    private ExchangeConversionService exchangeConversionService =
                    Mockito.mock(ExchangeConversionService.class);

    @Before
    public void beforeSet()
    {
        when(this.exchangeConversionService.exchangeHistory(anyString()))
                        .thenReturn(OzanExChangeTransaction.builder().amount(10.0).base("EUR")
                                        .conversion(95.2)
                                        .timestampCreated(DateUtils.parseDate("2020-12-17"))
                                        .dateCreated(DateUtils.parseDate("2020-12-17")).rate(9.52)
                                        .symbol("TRY").build());

        when(this.exchangeConversionService.exchangeHistory(anyString(), anyInt(), anyInt()))
                        .thenReturn(new PageImpl<>(
                                        List.of(OzanExChangeTransaction.builder().amount(10.0)
                                                        .base("EUR").conversion(95.2)
                                                        .timestampCreated(DateUtils.parseDate(
                                                                        "2020-12-17"))
                                                        .dateCreated(DateUtils
                                                                        .parseDate("2020-12-17"))
                                                        .rate(9.52).symbol("TRY").build())));

        when(this.exchangeConversionService
                        .exchangeHistoryByTransactionAndCreatedDate(anyString(), anyString(),
                                        anyInt(), anyInt())).thenReturn(new PageImpl<>(
                        List.of(OzanExChangeTransaction.builder().amount(10.0).base("EUR")
                                                        .conversion(96.2)
                                                        .timestampCreated(DateUtils.parseDate("2020-12-18"))
                                                        .dateCreated(DateUtils.parseDate("2020-12-18")).rate(9.62)
                                                        .symbol("TRY").build(),
                                        OzanExChangeTransaction.builder().amount(10.0).base("EUR")
                                                        .conversion(95.2).timestampCreated(
                                                        DateUtils.parseDate("2020-12-17"))
                                                        .dateCreated(DateUtils
                                                                        .parseDate("2020-12-17"))
                                                        .rate(9.52).symbol("TRY").build())));

    }

    @Test
    public void check_rate_and_conversion()
    {
        OzanExChangeTransaction exChangeTransaction =
                        this.exchangeConversionService.exchangeHistory(anyString());

        Assertions.assertNotNull(exChangeTransaction.getTransaction());

        Assertions.assertEquals(exChangeTransaction.getBase(), "EUR");
        Assertions.assertEquals(exChangeTransaction.getRate(), 9.52);
        Assertions.assertEquals(exChangeTransaction.getConversion(), 95.2);

    }

    @Test
    public void check_paging_rate_and_conversion()
    {
        Page<OzanExChangeTransaction> pageOzanExchangeTransaction = this.exchangeConversionService
                        .exchangeHistory(anyString(), anyInt(), anyInt());

        Assertions.assertNotNull(pageOzanExchangeTransaction);

        Assertions.assertEquals(pageOzanExchangeTransaction.getTotalPages(), 1);
        Assertions.assertEquals(pageOzanExchangeTransaction.getTotalElements(), 1);
        Assertions.assertEquals(pageOzanExchangeTransaction.getSize(), 1);

    }

    @Test
    public void check_paging_rate_and_conversion_transaction_date()
    {
        Page<OzanExChangeTransaction> pageOzanExchangeTransaction = this.exchangeConversionService
                        .exchangeHistoryByTransactionAndCreatedDate(anyString(), anyString(),
                                        anyInt(), anyInt());

        Assertions.assertNotNull(pageOzanExchangeTransaction);

        Assertions.assertEquals(pageOzanExchangeTransaction.getTotalPages(), 1);
        Assertions.assertEquals(pageOzanExchangeTransaction.getTotalElements(), 2);
        Assertions.assertEquals(pageOzanExchangeTransaction.getSize(), 2);

    }
}
