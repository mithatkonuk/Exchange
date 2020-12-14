package com.ozan.exchange.resource;

import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.service.ExchangeConversionService;
import com.ozan.exchange.web.util.Response;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/history" )
@AllArgsConstructor
public class ConversionHistoryController
{

    private final ExchangeConversionService exchangeConversionService;

    @GetMapping( "/conversion" )
    public Response conversion( @RequestParam( "transaction" ) String transaction,
                    @RequestParam( "date" ) String date )
    {

        if(null == transaction && null == date)
        {
            throw new ExchangeServiceParamException(
                            ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NOT_ACCEPTABLE,
                            "Given currency pair is not acceptable");
        }

        return Response.builder().data(null).build();
    }

}
