package com.ozan.exchange.controller;

import com.ozan.exchange.annotation.Logged;
import com.ozan.exchange.dto.Exchange;
import com.ozan.exchange.http.response.Response;
import com.ozan.exchange.provider.ForgienExchangeProvider;
import com.ozan.exchange.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping( "external-resource" )
@AllArgsConstructor
@Validated
public class ForgienExchangeExternalResource
{
    @Qualifier( "${forgien_exchange_providers.external.name}" )
    private final ForgienExchangeProvider forgienExchangeProvider;

    @Logged
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
}