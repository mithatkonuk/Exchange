package com.ozan.exchange.service;

import com.ozan.exchange.domain.ExchangeConversion;
import com.ozan.exchange.dto.ExchangeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.Optional;

public interface ExchangeConversionService
{
    ExchangeConversion exchangeHistory( final String transaction );

    Page<ExchangeConversion> exchangeHistory( final String dateCreated, int offset, int pageSize );

    Page<ExchangeConversion> exchangeHistoryByTransactionAndCreatedDate( String transaction,
                    String createdDate, int offset, int pageSize);

    ExchangeResponse saveExchangeHistory( String base, String symbol, Double amount, Double rate,
                    boolean detail );

}
