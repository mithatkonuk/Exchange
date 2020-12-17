package com.ozan.exchange.resource.unitTest.errorTest;

import com.ozan.exchange.exception.ExchangeServiceParamException;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.rateExchangeProvider.RateExchangeApiProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith( SpringExtension.class )
@SpringBootTest
public class ErrorCodeTest
{
    private RateExchangeApiProvider rateExchangeApiProvider =
                    Mockito.mock(RateExchangeApiProvider.class);

    @Before
    public void beforeSet()
    {
        when(rateExchangeApiProvider.getExchange(anyString(), anyString()))
                        .thenThrow(new ExchangeServiceParamException(
                                        ErrorCode.EXCHANGE_SERVICE.NOT_FOUND));
    }

    @Test( expected = ExchangeServiceParamException.class )
    public void check_invalid_input_check_throw_param_Exception()
    {

        this.rateExchangeApiProvider.getExchange("TEST", "TRY");

    }

}
