package com.ozan.exchange.controller;

import com.ozan.exchange.annotation.OzanExecutionTimeLogged;
import com.ozan.exchange.domain.Exchange;
import com.ozan.exchange.domain.ForgienConversion;
import com.ozan.exchange.error.ErrorCode;
import com.ozan.exchange.exception.ExternalServiceException;
import com.ozan.exchange.web.util.Response;
import com.ozan.exchange.provider.ForgienExchangeProvider;
import com.ozan.exchange.util.StringUtils;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping( "external-resource" )
@Validated
public class ForgienExchangeExternalResource
{
    private final ForgienExchangeProvider forgienExchangeProvider;

    // Dot use lombok @AllArgsConstructor lombok doesnt know about which Qualifier will be inject
    @Autowired
    public ForgienExchangeExternalResource( @Qualifier( "${forgien_exchange_providers.feign.name}" )
                    ForgienExchangeProvider forgienExchangeProvider )
    {
        this.forgienExchangeProvider = forgienExchangeProvider;
    }

    @OzanExecutionTimeLogged
    @GetMapping( value = "/exchange" )
    public Response exchange(
                    @NotNull @NotBlank @NotEmpty @RequestParam( "currencies" ) String currencies )
    {
        String[] currArr = currencies.split(",");

        if(StringUtils.isNotEmpty(currArr) && currArr.length == 2)
        {
            // TODO : check not supported money throw exception we need to handle those exceptions
            Exchange exchange = this.forgienExchangeProvider.getExchange(currArr[0], currArr[1]);
            return Response.builder().data(exchange).build();
        }
        else
        {
            throw new IllegalArgumentException("Given currency pair is not acceptable");
        }
    }

    @OzanExecutionTimeLogged
    @PostMapping( value = "/conversion" )
    public Response exchange( @RequestBody @NotNull @Valid ForgienConversion forgienConversion )
                    throws FeignException
    {
        try
        {
            // TODO : consider when throwing exception so what will we return !
            Exchange exchange = this.forgienExchangeProvider
                            .getExchange(forgienConversion.getBase(),
                                            forgienConversion.getSymbol());

            Double amount = forgienConversion.getAmount();

            Map<String, Double> rates = exchange.getRates();

            Double rate = rates.get(forgienConversion.getSymbol());

            if(null == rate)
            {
                rate = 0d;
            }

            forgienConversion.setConversion(rate * amount);
            forgienConversion.setDate(Date.from(Instant.now()));
            return Response.builder().data(forgienConversion).build();
        }
        catch( FeignException e )
        {
            throw new ExternalServiceException(
                            ErrorCode.EXTERNAL_SERVICE_PROVIDER.EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND,
                            "External service is not supporting given exchange");
        }

    }
}