package com.ozan.exchange.service;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.exception.ExchangeHistoryNotFoundException;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.repo.ExchangeConversionRepo;
import com.ozan.exchange.util.OzanAssertUtils;
import com.ozan.exchange.util.OzanCollectionUtils;
import com.ozan.exchange.util.OzanDateUtils;
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
        OzanAssertUtils.assertIsBlank(transaction);
        OzanAssertUtils.assertTransaction(transaction);

        Optional<OzanExChangeTransaction> exchangeConversion =
                        exchangeConversionRepo.findByTransaction(UUID.fromString(transaction));

        return exchangeConversion.orElseThrow(() -> new ExchangeHistoryNotFoundException(
                        ErrorCode.EXCHANGE_SERVICE.NOT_FOUND));
    }

    @Override
    public Page<OzanExChangeTransaction> exchangeHistory( final String dateCreated, int offset,
                    int pageSize )
    {

        OzanAssertUtils.assertIsBlank(dateCreated);
        OzanAssertUtils.assertDateFormat(dateCreated);

        Page<OzanExChangeTransaction> exchangeConversions = exchangeConversionRepo
                        .findByDateCreated(OzanDateUtils.fromString(dateCreated,
                                        OzanDateUtils.YYYY_MM_DD),
                                        PageRequest.of(offset, pageSize));

        if(OzanCollectionUtils.isEmpty(exchangeConversions.getContent()))
        {
            throw new ExchangeHistoryNotFoundException(ErrorCode.EXCHANGE_SERVICE.NOT_FOUND);
        }

        return exchangeConversions;
    }

    @Override
    public Page<OzanExChangeTransaction> exchangeHistoryByTransactionAndCreatedDate(
                    String transaction, String createdDate, int offset, int pageSize )
    {
        OzanAssertUtils.assertNotNullPair(transaction, createdDate);
        OzanAssertUtils.assertDateFormat(createdDate);
        OzanAssertUtils.assertTransaction(transaction);

        Pageable pageable = PageRequest.of(offset, pageSize);

        List<OzanExChangeTransaction> ozanExChangeTransactionList =
                        exchangeConversionRepo.exchangeHistory(transaction, createdDate, pageable);

        OzanAssertUtils.assertCollectionIsEmpty(ozanExChangeTransactionList,
                        ErrorCode.EXCHANGE_SERVICE.NOT_FOUND);

        return new PageImpl<>(ozanExChangeTransactionList, pageable,
                        exchangeConversionRepo.count());
    }

    @Transactional( rollbackFor = DataAccessException.class )
    @Override
    public OzanExchange saveExchangeHistory( String base, String symbol, Double amount, Double rate,
                    boolean detail )
    {
        rate = (null == rate) ? 0d : rate;

        OzanExChangeTransaction ozanExChangeTransaction =
                        OzanExChangeTransaction.builder().base(base).symbol(symbol).amount(amount)
                                        .rate(rate).conversion(rate * amount)
                                        .dateCreated(OzanDateUtils.nowAsDate())
                                        .timestampCreated(OzanDateUtils.nowAsDate()).build();

        OzanExChangeTransaction saved = exchangeConversionRepo.save(ozanExChangeTransaction);

        if(detail)
        {

            return OzanExchange.builder().amount(amount).base(base).symbol(symbol)
                            .conversion(rate * amount).date(OzanDateUtils.nowAsDate()).rate(rate)
                            .transaction(saved.getTransaction().toString()).build();

        }
        else
        {

            return OzanExchange.builder().transaction(saved.getTransaction().toString())
                            .conversion(rate * amount).build();
        }

    }

}
