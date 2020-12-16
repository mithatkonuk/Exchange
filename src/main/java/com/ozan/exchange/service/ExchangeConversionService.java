package com.ozan.exchange.service;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import com.ozan.exchange.dto.OzanExchange;
import org.springframework.data.domain.Page;

public interface ExchangeConversionService
{
    OzanExChangeTransaction exchangeHistory( final String transaction );

    Page<OzanExChangeTransaction> exchangeHistory( final String dateCreated, int offset, int pageSize );

    Page<OzanExChangeTransaction> exchangeHistoryByTransactionAndCreatedDate( String transaction,
                    String createdDate, int offset, int pageSize);

    OzanExchange saveExchangeHistory( String base, String symbol, Double amount, Double rate,
                    boolean detail );

}
