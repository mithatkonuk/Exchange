package com.ozan.exchange.repo;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Exchange Transaction Repo
 * operation on requested conversion ( exchange transaction)
 */
public interface DefaultOzanExchangeRepo
{
    List<OzanExChangeTransaction> exchangeHistory( String transaction, String createdDate,
                    Pageable pageable );
}
