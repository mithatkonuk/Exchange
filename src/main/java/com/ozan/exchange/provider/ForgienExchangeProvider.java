package com.ozan.exchange.provider;

import com.ozan.exchange.dto.Exchange;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface ForgienExchangeProvider
{
    @RequestMapping( method = RequestMethod.GET, value = "${forgien_exchange_providers.common.exchange_path}" )
    Exchange getExchange( @RequestParam( "base" ) String base,
                    @RequestParam( "sources" ) String symbols );
}
