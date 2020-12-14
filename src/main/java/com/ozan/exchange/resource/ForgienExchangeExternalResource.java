package com.ozan.exchange.resource;

import com.ozan.exchange.annotation.OzanExecutionTimeLogged;
import com.ozan.exchange.dto.Exchange;
import com.ozan.exchange.dto.ExchangeRequest;
import com.ozan.exchange.dto.ExchangeResponse;
import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.ExternalServiceException;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.rateProvider.RateApiProvider;
import com.ozan.exchange.service.ExchangeConversionService;
import com.ozan.exchange.util.DateUtils;
import com.ozan.exchange.util.StringUtils;
import com.ozan.exchange.web.util.Response;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@RestController
@RequestMapping( "external-resource" )
@Validated
public class ForgienExchangeExternalResource
{
    private final RateApiProvider rateApiProvider;
    private final ExchangeConversionService exchangeConversionService;

    // ATTENTION : Dont use lombok @AllArgsConstructor lombok doesnt know about which Qualifier will be inject
    @Autowired
    public ForgienExchangeExternalResource( RateApiProvider rateApiProvider,
                    ExchangeConversionService exchangeConversionService )
    {
        this.rateApiProvider = rateApiProvider;
        this.exchangeConversionService = exchangeConversionService;
    }

    @OzanExecutionTimeLogged
    @GetMapping( value = "/exchange" )
    public Response exchange(
                    @NotNull @NotBlank @NotEmpty @RequestParam( "currencies" ) String currencies )
    {
        String[] currArr = currencies.split(",");

        if(StringUtils.isNotEmpty(currArr))
        {
            Exchange exchange = this.rateApiProvider.getExchange(currArr[0], currArr[1]);

            return Response.builder().data(exchange).build();
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
    public Response exchange( @RequestBody @NotNull @Valid ExchangeRequest exchangeRequest )
    {

        // request from external service
        Exchange exchange = this.rateApiProvider
                        .getExchange(exchangeRequest.getBase(), exchangeRequest.getSymbol());

        // calculate given amount
        Double amount = exchangeRequest.getAmount();

        Double rate = exchange.getRates().get(exchangeRequest.getSymbol());

        if(null == rate)
        {
            rate = 0d;
        }

        ExchangeResponse exchangeResponse =
                        ExchangeResponse.builder().amount(exchangeRequest.getAmount())
                                        .base(exchangeRequest.getBase())
                                        .symbol(exchangeRequest.getSymbol())
                                        .conversion(rate * amount).date(DateUtils.nowAsDate())
                                        .token(exchangeRequest.getToken())
                                        .rate(rate).build();

        // save exchange transaction
        exchangeConversionService.saveConversion(exchangeResponse);

        // return calculate amount with rate
        return Response.builder().data(exchangeResponse).build();

    }

    @OzanExecutionTimeLogged
    @GetMapping( value = "/conversion" )
    public Response exchange( @RequestParam( "base" ) String base,
                    @RequestParam( "symbol" ) String symbol,
                    @RequestParam( "amount" ) Double amount )
    {

        // request from external service
        Exchange exchange = this.rateApiProvider.getExchange(base, symbol);

        // calculate given amount
        Double rate = exchange.getRates().get(symbol);

        if(null == rate)
        {
            rate = 0d;
        }

        ExchangeResponse exchangeResponse =
                        ExchangeResponse.builder().amount(amount).base(base).symbol(symbol)
                                        .conversion(rate * amount).date(DateUtils.nowAsDate())
                                        .rate(rate).build();

        // save exchange transaction
        exchangeConversionService.saveConversion(exchangeResponse);

        // return calculate amount with rate
        return Response.builder().data(rate * amount).build();

    }
}