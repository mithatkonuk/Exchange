package com.ozan.exchange.resource;

import com.ozan.exchange.annotation.OzanExecutionTimeLogged;
import com.ozan.exchange.dto.Conversion;
import com.ozan.exchange.dto.ExternalExchange;
import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.rateProvider.RateApiProvider;
import com.ozan.exchange.service.ExchangeConversionService;
import com.ozan.exchange.util.DateUtils;
import com.ozan.exchange.util.StringUtils;
import com.ozan.exchange.web.util.Response;
import com.ozan.exchange.web.util.ResponseError;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping( "exchange-service" )
@Validated
@AllArgsConstructor
public class OzanExchangeApi
{
    private final RateApiProvider rateApiProvider;
    private final ExchangeConversionService exchangeConversionService;

    @OzanExecutionTimeLogged
    @GetMapping( value = "/exchange" )
    public Response exchange(
                    @NotNull @NotBlank @NotEmpty @RequestParam( "currencies" ) String currencies )
    {
        String[] currArr = StringUtils.split(currencies, ",");

        if(StringUtils.isNotEmpty(currArr))
        {
            ExternalExchange externalExchange =
                            this.rateApiProvider.getExchange(currArr[0], currArr[1]);
            return Response.builder().data(OzanExchange.builder().base(externalExchange.getBase())
                            .symbol(currArr[1]).rate(externalExchange.getRates().get(currArr[1]))
                            .date(DateUtils.nowAsDate()).build())
                            .error(ResponseError.EMPTY_RESPONSE_ERROR).build();
        }
        else
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NOT_ACCEPTABLE,
                            "Given currency pair is not acceptable");
        }

    }

    @OzanExecutionTimeLogged
    @PostMapping( value = "/conversion" )
    public Response exchange( @RequestBody @NotNull @Valid Conversion conversion )
    {

        // request from external service
        ExternalExchange externalExchange = this.rateApiProvider
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
    @PutMapping( value = "/conversion" )
    public Response exchange( @RequestParam( "base" ) @NotNull @NotBlank String base,
                    @RequestParam( "symbol" ) @NotNull @NotBlank String symbol,
                    @RequestParam( "amount" )
                    @PositiveOrZero( message = "Amount should be positive or zero" ) Double amount,
                    @RequestParam( name = "detail",
                                   defaultValue = "false" ) Boolean detail )
    {

        // request from external service
        ExternalExchange externalExchange = this.rateApiProvider.getExchange(base, symbol);

        // return calculate amount with rate
        return Response.builder().data(exchangeConversionService
                        .saveExchangeHistory(base, symbol, amount,
                                        externalExchange.getRates().get(symbol), detail))
                        .error(ResponseError.EMPTY_RESPONSE_ERROR).build();

    }
}