package com.ozan.exchange.repo;

import com.ozan.exchange.domain.ExchangeConversion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExchangeConversionRepo extends CrudRepository<ExchangeConversion, Long>
{
    Optional<ExchangeConversion> findByTransaction( UUID transaction );

    Optional<List<ExchangeConversion>> findByDateCreated( Date dateCreated );
}
