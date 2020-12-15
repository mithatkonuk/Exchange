package com.ozan.exchange.repo;

import com.ozan.exchange.domain.ExchangeConversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeConversionRepo
                extends PagingAndSortingRepository<ExchangeConversion, Long>,
                DefaultOzanExchangeRepo
{
    Optional<ExchangeConversion> findByTransaction( UUID transaction );

    Page<ExchangeConversion> findByDateCreated( Date dateCreated, Pageable pageable );
}
