package com.ozan.exchange.resource;

import com.ozan.exchange.domain.ExchangeConversion;
import com.ozan.exchange.dto.OzanExchangePaging;
import com.ozan.exchange.service.ExchangeConversionService;
import com.ozan.exchange.web.util.Response;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/history" )
@AllArgsConstructor
public class ConversionHistoryController
{
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String DEFAULT_OFFSET_SIZE = "0";

    private final ExchangeConversionService exchangeConversionService;

    @GetMapping( "/conversion/transaction" )
    public Response conversionByTransaction( @RequestParam( "id" ) String transaction )
    {
        ExchangeConversion exchangeConversion =
                        exchangeConversionService.exchangeHistory(transaction);

        return Response.builder().data(exchangeConversion).build();
    }

    @GetMapping( "/conversion/date" )
    public Response conversionByDate(
                    @RequestParam( value = "created", defaultValue = "" ) String date,
                    @RequestParam( value = "offset", defaultValue = DEFAULT_OFFSET_SIZE )
                                    Integer offset,
                    @RequestParam( value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE )
                                    Integer pageSize )
    {
        Page<ExchangeConversion> exchangeConversions =
                        exchangeConversionService.exchangeHistory(date, offset, pageSize);

        OzanExchangePaging ozanExchangePaging = OzanExchangePaging
                        .of(exchangeConversions.getContent(),
                                        exchangeConversions.getTotalElements(),
                                        exchangeConversions.getPageable().getPageNumber(),
                                        exchangeConversions.getPageable().getPageSize(),
                                        exchangeConversions.getTotalPages());

        return Response.builder().data(ozanExchangePaging).build();
    }

    @GetMapping( "/conversion" )
    public Response conversionByDate(
                    @RequestParam( value = "created", defaultValue = "" ) String date,
                    @RequestParam( value = "transaction", defaultValue = "" ) String transaction,
                    @RequestParam( value = "offset", defaultValue = DEFAULT_OFFSET_SIZE )
                                    Integer offset,
                    @RequestParam( value = "pageSize", defaultValue = DEFAULT_PAGE_SIZE )
                                    Integer pageSize )
    {

        Page<ExchangeConversion> exchangeConversions = exchangeConversionService
                        .exchangeHistoryByTransactionAndCreatedDate(transaction, date, offset,
                                        pageSize);

        OzanExchangePaging ozanExchangePaging = OzanExchangePaging
                        .of(exchangeConversions.getContent(),
                                        exchangeConversions.getTotalElements(),
                                        exchangeConversions.getPageable().getPageNumber(),
                                        exchangeConversions.getPageable().getPageSize(),
                                        exchangeConversions.getTotalPages());

        return Response.builder().data(ozanExchangePaging).build();
    }
}
