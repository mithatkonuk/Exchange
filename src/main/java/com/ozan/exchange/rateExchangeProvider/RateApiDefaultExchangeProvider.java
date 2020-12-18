package com.ozan.exchange.rateExchangeProvider;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * RateApiDefaultExchangeProvider is a Feign Client
 * which is wrapper ForgienExchangeProvider
 *
 * @author mithat.konuk
 */
@Qualifier( "${forgien_exchange_providers.feign.name}" )
@FeignClient( value = "${forgien_exchange_providers.feign.name}",
              url = "${forgien_exchange_providers.feign.url}" )
public interface RateApiDefaultExchangeProvider extends ForgienExchangeProvider
{

}