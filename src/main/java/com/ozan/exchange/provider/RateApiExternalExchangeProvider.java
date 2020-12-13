package com.ozan.exchange.provider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;

@Qualifier( "${forgien_exchange_providers.default.name}" )
@FeignClient( value = "${forgien_exchange_providers.default.name}", url = "${forgien_exchange_providers.default.url}" )
public interface RateApiExternalExchangeProvider extends ForgienExchangeProvider
{

}