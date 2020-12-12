package com.ozan.exchange.foreign.exchange.provider;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient( value = "rate-api-exchange-provider", url = "${forgien_exchange_providers.url}" )
public interface RateApiExternalExchangeProvider extends ForgienExchangeProvider
{

}