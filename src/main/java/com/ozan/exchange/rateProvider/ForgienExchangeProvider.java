package com.ozan.exchange.rateProvider;

import com.ozan.exchange.dto.ExternalExchange;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface ForgienExchangeProvider
{
    @RequestMapping( method = RequestMethod.GET, value = "${forgien_exchange_providers.feign.exchange_path}" )
    ExternalExchange getExchange( @RequestParam( "base" ) String base,
                    @RequestParam( "symbols" ) String symbols );
}
