package com.ozan.exchange.resource;

import com.ozan.exchange.annotation.OzanExecutionTimeLogged;
import com.ozan.exchange.dto.Conversion;
import com.ozan.exchange.dto.ExternalExchange;
import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.rateExchangeProvider.RateExchangeApiProvider;
import com.ozan.exchange.service.ExchangeConversionService;
import com.ozan.exchange.util.OzanDateUtils;
import com.ozan.exchange.util.OzanStringUtils;
import com.ozan.exchange.web.util.Response;
import com.ozan.exchange.web.util.ResponseError;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping( "exchangeApi" )
@Validated
@AllArgsConstructor
public class OzanExchangeApi
{
    private final RateExchangeApiProvider rateExchangeApiProvider;
    private final ExchangeConversionService exchangeConversionService;

    @OzanExecutionTimeLogged
    @GetMapping( value = "/exchange" )
    public Response exchange( @ApiParam( "Currency pair , [source: EUR,target:TRY]" )
    @NotNull @NotBlank @NotEmpty @RequestParam( "currency_pair" ) String currencies )
    {
        String[] currArr = OzanStringUtils.split(currencies, ",");

        if(OzanStringUtils.isNotEmpty(currArr) && currArr.length == OzanStringUtils.PAIR_LENGHT)
        {
            ExternalExchange externalExchange =
                            this.rateExchangeApiProvider.getExchange(currArr[0], currArr[1]);
            return Response.builder().data(OzanExchange.builder().base(externalExchange.getBase())
                            .symbol(currArr[1]).rate(externalExchange.getRates().get(currArr[1]))
                            .date(OzanDateUtils.nowAsDate()).build())
                            .error(ResponseError.EMPTY_RESPONSE_ERROR).build();
        }
        else
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NOT_ACCEPTABLE,
                            "Given currency pair is not acceptable for exchange rate");
        }

    }

    @OzanExecutionTimeLogged
    @PostMapping( value = "/exchange/conversion",
                  produces = "application/json",
                  consumes = "application/json" )
    public Response exchange(
                    @ApiParam( "Represent to calculate amount between currencies" ) @RequestBody
                    @NotNull @Valid Conversion conversion )
    {

        // request from external service
        ExternalExchange externalExchange = this.rateExchangeApiProvider
                        .getExchange(conversion.getBase(), conversion.getSymbol());

        // return calculate amount with rate
        return Response.builder().data(exchangeConversionService
                        .saveExchangeHistory(conversion.getBase(), conversion.getSymbol(),
                                        conversion.getAmount(),
                                        externalExchange.getRates().get(conversion.getSymbol()),
                                        conversion.isDetail()))
                        .error(ResponseError.EMPTY_RESPONSE_ERROR).build();
    }

    @OzanExecutionTimeLogged
    @GetMapping( value = "/exchange/conversion" )
    public Response exchange( @ApiParam( "Represent to source currency" ) @RequestParam( "base" )
    @NotNull @NotBlank String base,
                    @ApiParam( "Represent to target currency" ) @RequestParam( "symbol" )
                    @NotNull @NotBlank String symbol,
                    @ApiParam( "Represent to amount of base currency" ) @RequestParam( "amount" )
                    @PositiveOrZero( message = "Amount should be positive or zero" ) Double amount,
                    @ApiParam( "detail enable/disable for response" )
                    @RequestParam( name = "detail",
                                   defaultValue = "false" ) Boolean detail )
    {

        // request from external service
        ExternalExchange externalExchange = this.rateExchangeApiProvider.getExchange(base, symbol);

        // return calculate amount with rate
        return Response.builder().data(exchangeConversionService
                        .saveExchangeHistory(base, symbol, amount,
                                        externalExchange.getRates().get(symbol), detail))
                        .error(ResponseError.EMPTY_RESPONSE_ERROR).build();

    }
}