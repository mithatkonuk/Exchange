package com.ozan.exchange.foreign.Exchange.service;

import com.ozan.exchange.foreign.Exchange.provider.ForgienExchangeProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ForgienExchangeService
{

    private final ForgienExchangeProvider forgienExchangeProvider;
}
