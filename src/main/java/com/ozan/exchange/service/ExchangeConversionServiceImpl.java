package com.ozan.exchange.service;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.exception.ExchangeHistoryNotFoundException;
import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.repo.ExchangeConversionRepo;
import com.ozan.exchange.util.AssertUtils;
import com.ozan.exchange.util.CollectionUtils;
import com.ozan.exchange.util.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service( "exchangeConversionService" )
public class ExchangeConversionServiceImpl implements ExchangeConversionService
{

    private final ExchangeConversionRepo exchangeConversionRepo;

    @Override
    public OzanExChangeTransaction exchangeHistory( final String transaction )
    {
        AssertUtils.assertIsBlank(transaction);
        AssertUtils.assertTransaction(transaction);

        Optional<OzanExChangeTransaction> exchangeConversion =
                        exchangeConversionRepo.findByTransaction(UUID.fromString(transaction));

        return exchangeConversion.orElseThrow(() -> new ExchangeServiceParamException(
                        ErrorCode.EXCHANGE_SERVICE.NOT_FOUND));
    }

    @Override
    public Page<OzanExChangeTransaction> exchangeHistory( final String dateCreated, int offset,
                    int pageSize )
    {

        AssertUtils.assertIsBlank(dateCreated);

        Page<OzanExChangeTransaction> exchangeConversions = exchangeConversionRepo
                        .findByDateCreated(DateUtils.fromString(dateCreated, DateUtils.YYYY_MM_DD),
                                        PageRequest.of(offset, pageSize));

        if(CollectionUtils.isEmpty(exchangeConversions.getContent()))
        {
            throw new ExchangeHistoryNotFoundException(ErrorCode.EXCHANGE_SERVICE.NOT_FOUND);
        }

        return exchangeConversions;
    }

    @Override
    public Page<OzanExChangeTransaction> exchangeHistoryByTransactionAndCreatedDate( String transaction,
                    String createdDate, int offset, int pageSize )
    {
        AssertUtils.assertNotNullPair(transaction, createdDate);

        Pageable pageable = PageRequest.of(offset, pageSize);

        List<OzanExChangeTransaction> ozanExChangeTransactionList =
                        exchangeConversionRepo.exchangeHistory(transaction, createdDate, pageable);

        AssertUtils.assertCollectionIsEmpty(ozanExChangeTransactionList,
                        ErrorCode.EXCHANGE_SERVICE.NOT_FOUND);

        return new PageImpl<>(ozanExChangeTransactionList, pageable, exchangeConversionRepo.count());
    }

    @Transactional( rollbackFor = DataAccessException.class )
    @Override
    public OzanExchange saveExchangeHistory( String base, String symbol, Double amount,
                    Double rate, boolean detail )
    {
        rate = (null == rate) ? 0d : rate;

        OzanExChangeTransaction ozanExChangeTransaction =
                        OzanExChangeTransaction.builder().base(base).symbol(symbol).amount(amount)
                                        .range(rate).conversion(rate * amount)
                                        .dateCreated(DateUtils.nowAsDate())
                                        .timestampCreated(DateUtils.nowAsDate()).build();

        OzanExChangeTransaction saved = exchangeConversionRepo.save(ozanExChangeTransaction);

        if(detail)
        {

            return OzanExchange.builder().amount(amount).base(base).symbol(symbol)
                            .conversion(rate * amount).date(DateUtils.nowAsDate()).rate(rate)
                            .transaction(saved.getTransaction().toString()).build();

        }
        else
        {

            return OzanExchange.builder().transaction(saved.getTransaction().toString())
                            .conversion(rate * amount).build();
        }

    }

}
