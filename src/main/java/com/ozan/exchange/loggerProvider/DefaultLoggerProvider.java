package com.ozan.exchange.loggerProvider;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;

@AllArgsConstructor
public class DefaultLoggerProvider extends LoggerProvider
{
    private final Logger logger;


}
