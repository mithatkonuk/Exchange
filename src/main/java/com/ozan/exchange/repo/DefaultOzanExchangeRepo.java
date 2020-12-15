package com.ozan.exchange.repo;

import com.ozan.exchange.domain.ExchangeConversion;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface DefaultOzanExchangeRepo
{
    List<ExchangeConversion> exchangeHistory( String transaction,
                    String createdDate, Pageable pageable );
}
