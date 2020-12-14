package com.ozan.exchange.service;

import com.ozan.exchange.domain.ExchangeConversion;
import com.ozan.exchange.dto.ExchangeResponse;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExchangeConversionService
{
    Optional<ExchangeConversion> conversions( final String transaction );

    Optional<List<ExchangeConversion>> conversions( final Date dateCreated );

    void saveConversion( ExchangeResponse response );

}
