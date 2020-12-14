package com.ozan.exchange.service;

import com.ozan.exchange.domain.ExchangeConversion;
import com.ozan.exchange.dto.ExchangeResponse;
import com.ozan.exchange.repo.ExchangeConversionRepo;
import com.ozan.exchange.util.AssertUtils;
import com.ozan.exchange.util.DateUtils;
import lombok.AllArgsConstructor;
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
    public Optional<List<ExchangeConversion>> conversions( final Date dateCreated )
    {
        AssertUtils.assertNotNull(dateCreated);
        return exchangeConversionRepo.findByDateCreated(dateCreated);
    }

    @Transactional
    @Override
    public void saveConversion( final ExchangeResponse response )
    {
        ExchangeConversion exchangeConversion =
                        ExchangeConversion.builder().base(response.getBase())
                                        .symbol(response.getSymbol()).amount(response.getAmount())
                                        .range(response.getRate())
                                        .conversion(response.getConversion())
                                        .dateCreated(response.getDate())
                                        .timestampCreated(DateUtils.nowAsDate()).build();

        exchangeConversionRepo.save(exchangeConversion);

    }

}
