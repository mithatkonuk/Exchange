package com.ozan.exchange.provider;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient( value = "${forgien_exchange_providers.external.name}", url = "${forgien_exchange_providers.external.url}" )
public interface RateApiExternalExchangeProvider extends ForgienExchangeProvider
{

}