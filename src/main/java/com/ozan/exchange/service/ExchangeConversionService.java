package com.ozan.exchange.service;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import com.ozan.exchange.dto.OzanExchange;
import org.springframework.data.domain.Page;

/**
 * Exchange Conversion functionalities
 *
 * @author mithat.konuk
 */
public interface ExchangeConversionService
{
    OzanExChangeTransaction exchangeHistory( final String transaction );

    Page<OzanExChangeTransaction> exchangeHistory( final String dateCreated, int offset,
                    int pageSize );

    Page<OzanExChangeTransaction> exchangeHistoryByTransactionAndCreatedDate(
                    final String transaction, final String createdDate, final int offset,
                    final int pageSize );

    OzanExchange saveExchangeHistory( final String base, final String symbol, final Double amount,
                    final Double rate, final boolean detail );

}
