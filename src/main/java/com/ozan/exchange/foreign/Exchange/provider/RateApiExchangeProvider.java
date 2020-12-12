package com.ozan.exchange.foreign.Exchange.provider;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient( value = "rate-api-exchange-provider", url = "${forgien_exchange_providers.url}" )
public interface RateApiExchangeProvider extends ForgienExchangeProvider
{

}