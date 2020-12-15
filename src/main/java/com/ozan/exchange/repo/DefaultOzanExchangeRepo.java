package com.ozan.exchange.repo;

import com.ozan.exchange.domain.ExchangeConversion;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DefaultOzanExchangeRepo
{
    List<ExchangeConversion> exchangeHistory( String transaction, String createdDate,
                    Pageable pageable );
}
