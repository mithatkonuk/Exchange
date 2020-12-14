package com.ozan.exchange.service;

import com.ozan.exchange.domain.ExchangeConversion;
import com.ozan.exchange.dto.ExchangeResponse;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExchangeConversionService
{
    Optional<ExchangeConversion> conversions( final String transaction );

    Page<ExchangeConversion> conversions( final Date dateCreated, int offset, int pageSize );

    ExchangeResponse saveConversion( String base, String symbol, Double amount, Double rate,
                    boolean detail );

}
