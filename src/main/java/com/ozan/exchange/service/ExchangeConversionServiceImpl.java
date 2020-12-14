package com.ozan.exchange.service;

import com.ozan.exchange.domain.ExchangeConversion;
import com.ozan.exchange.dto.ExchangeResponse;
import com.ozan.exchange.repo.ExchangeConversionRepo;
import com.ozan.exchange.util.AssertUtils;
import com.ozan.exchange.util.DateUtils;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service( "exchangeConversionService" )
public class ExchangeConversionServiceImpl implements ExchangeConversionService
{

    private final ExchangeConversionRepo exchangeConversionRepo;

    @Override
    public Optional<ExchangeConversion> conversions( final String transaction )
    {
        AssertUtils.assertTransaction(transaction);
        return exchangeConversionRepo.findByTransaction(UUID.fromString(transaction));
    }

    @Override
    public Page<ExchangeConversion> conversions( final Date dateCreated, int offset,
                    int pageSize )
    {
        AssertUtils.assertNotNull(dateCreated);
        return exchangeConversionRepo
                        .findByDateCreated(dateCreated, PageRequest.of(offset, pageSize));
    }

    @Transactional( rollbackOn = DataAccessException.class )
    @Override
    public ExchangeResponse saveConversion( String base, String symbol, Double amount, Double rate,
                    boolean detail )
    {
        rate = (null == rate) ? 0d : rate;

        ExchangeConversion exchangeConversion =
                        ExchangeConversion.builder().base(base).symbol(symbol).amount(amount)
                                        .range(rate).conversion(rate * amount)
                                        .dateCreated(DateUtils.nowAsDate())
                                        .timestampCreated(DateUtils.nowAsDate()).build();

        ExchangeConversion saved = exchangeConversionRepo.save(exchangeConversion);

        if(detail)
        {

            return ExchangeResponse.builder().amount(amount).base(base).symbol(symbol)
                            .conversion(rate * amount).date(DateUtils.nowAsDate()).rate(rate)
                            .transaction(saved.getTransaction().toString()).build();

        }
        else
        {

            return ExchangeResponse.builder().transaction(saved.getTransaction().toString())
                            .conversion(rate * amount).build();
        }

    }

}
