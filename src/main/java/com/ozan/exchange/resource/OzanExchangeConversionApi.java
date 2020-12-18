package com.ozan.exchange.resource;

import com.ozan.exchange.domain.OzanExChangeTransaction;
import com.ozan.exchange.dto.OzanPaging;
import com.ozan.exchange.service.ExchangeConversionService;
import com.ozan.exchange.util.OzanObjectMapperUtils;
import com.ozan.exchange.web.util.Response;
import com.ozan.exchange.web.util.ResponseError;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.PositiveOrZero;

/**
 * Conversion Api
 *
 * @author mithat.konuk
 */
@RestController
@RequestMapping( "/conversionApi" )
@AllArgsConstructor
@Validated
public class OzanExchangeConversionApi
{
    private static final String DEFAULT_PAGE_SIZE = "10";
    private static final String DEFAULT_OFFSET_SIZE = "0";

    private static final Logger logger = LoggerFactory.getLogger(OzanExchangeConversionApi.class);

    private final ExchangeConversionService exchangeConversionService;

    @GetMapping( "/conversion/transaction" )
    public Response conversionByTransaction(
                    @ApiParam( "Transaction is type of UUID, please provide format by UUID" )
                    @RequestParam( value = "transaction_id",
                                   defaultValue = "" ) String transaction )
    {
        OzanExChangeTransaction ozanExChangeTransaction =
                        exchangeConversionService.exchangeHistory(transaction);

        logger.info(ozanExChangeTransaction.toString());

        return Response.builder().data(OzanObjectMapperUtils.mapper(ozanExChangeTransaction))
                        .error(ResponseError.EMPTY_RESPONSE_ERROR).build();
    }

    @GetMapping( "/conversion/date" )
    public Response conversionByDate(
                    @ApiParam( "Represent to date ,format [YYYY-MM-DD] default value empty" )
                    @RequestParam( value = "created",
                                   defaultValue = "" ) String date, @RequestParam( value = "offset",
                                                                                   defaultValue = DEFAULT_OFFSET_SIZE )
    @PositiveOrZero( message = "offset should be positive or zero" ) Integer offset,
                    @RequestParam( value = "pageSize",
                                   defaultValue = DEFAULT_PAGE_SIZE )
                    @PositiveOrZero( message = "page size  should be positive or zero" ) Integer pageSize )
    {
        Page<OzanExChangeTransaction> exchangeConversions =
                        exchangeConversionService.exchangeHistory(date, offset, pageSize);

        OzanPaging ozanPaging = OzanObjectMapperUtils.mapper(exchangeConversions);

        logger.info(ozanPaging.toString());

        return Response.builder().data(ozanPaging).error(ResponseError.EMPTY_RESPONSE_ERROR)
                        .build();
    }

    @GetMapping( "/conversion" )
    public Response conversion( @RequestParam( value = "created",
                                               defaultValue = "" )
    @ApiParam( "Represent to date , format [YYYY-MM-DD] , default value empty" ) String date,

                    @ApiParam( "Transaction is type of UUID, please provide format by UUID default value empty" )
                    @RequestParam( value = "transaction_id",
                                   defaultValue = "" ) String transaction,

                    @RequestParam( value = "offset",
                                   defaultValue = DEFAULT_OFFSET_SIZE )
                    @PositiveOrZero( message = "offset  size  should be positive or zero" ) Integer offset,

                    @RequestParam( value = "pageSize",
                                   defaultValue = DEFAULT_PAGE_SIZE )
                    @PositiveOrZero( message = "page size  should be positive or zero" ) Integer pageSize )
    {

        Page<OzanExChangeTransaction> exchangeConversions = exchangeConversionService
                        .exchangeHistoryByTransactionAndCreatedDate(transaction, date, offset,
                                        pageSize);

        OzanPaging ozanPaging = OzanObjectMapperUtils.mapper(exchangeConversions);

        logger.info(ozanPaging.toString());

        return Response.builder().data(ozanPaging).error(ResponseError.EMPTY_RESPONSE_ERROR)
                        .build();
    }
}
