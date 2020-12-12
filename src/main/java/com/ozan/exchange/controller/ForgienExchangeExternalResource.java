package com.ozan.exchange.controller;

import com.ozan.exchange.dto.Exchange;
import com.ozan.exchange.http.response.Response;
import com.ozan.exchange.provider.ForgienExchangeProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController( "/exchange" )
@AllArgsConstructor
public class ForgienExchangeExternalResource
{
    @Qualifier("rateApiExternalExchangeProvider")
    private final ForgienExchangeProvider forgienExchangeProvider;

    @GetMapping( value = "/exchange" )
    public Response exchange( @NotNull @RequestParam( "base" ) String base,
                    @NotNull @RequestParam( "symbols" ) String symbols )
    {

        Exchange exchange = this.forgienExchangeProvider.getExchange(base, symbols);

        return Response.builder().data(exchange).build();
    }
}
