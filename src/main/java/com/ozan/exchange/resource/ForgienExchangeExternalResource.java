package com.ozan.exchange.resource;

import com.ozan.exchange.annotation.OzanExecutionTimeLogged;
import com.ozan.exchange.dto.Exchange;
import com.ozan.exchange.dto.ExchangeRequest;
import com.ozan.exchange.dto.ExchangeResponse;
import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.rateProvider.RateApiProvider;
import com.ozan.exchange.service.ExchangeConversionService;
import com.ozan.exchange.util.DateUtils;
import com.ozan.exchange.util.StringUtils;
import com.ozan.exchange.web.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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

            ExchangeResponse exchangeResponse =
                            ExchangeResponse.builder().base(exchange.getBase()).symbol(currArr[1])
                                            .rate(exchange.getRates().get(currArr[1]))
                                            .date(DateUtils.nowAsDate())
                                            .build();
            return Response.builder().data(exchangeResponse).build();
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

        // return calculate amount with rate
        return Response.builder().data(exchangeConversionService
                        .saveConversion(exchangeRequest.getBase(), exchangeRequest.getSymbol(),
                                        exchangeRequest.getAmount(),
                                        exchange.getRates().get(exchangeRequest.getSymbol()),
                                        exchangeRequest.isDetail())).build();
    }

    @OzanExecutionTimeLogged
    @PutMapping( value = "/conversion" )
    public Response exchange( @RequestParam( "base" ) String base,
                    @RequestParam( "symbol" ) String symbol,
                    @RequestParam( "amount" ) Double amount,
                    @RequestParam( name = "detail", defaultValue = "false" ) Boolean detail )
    {

        // request from external service
        Exchange exchange = this.rateApiProvider.getExchange(base, symbol);

        // return calculate amount with rate
        return Response.builder().data(exchangeConversionService
                        .saveConversion(base, symbol, amount, exchange.getRates().get(symbol),
                                        detail)).build();

    }
}