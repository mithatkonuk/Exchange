package com.ozan.exchange.repo;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeConversionRepo
                extends PagingAndSortingRepository<OzanExChangeTransaction, Long>,
                DefaultOzanExchangeRepo
{
    Optional<OzanExChangeTransaction> findByTransaction( String transaction );

    Page<OzanExChangeTransaction> findByDateCreated( Date dateCreated, Pageable pageable );
}
