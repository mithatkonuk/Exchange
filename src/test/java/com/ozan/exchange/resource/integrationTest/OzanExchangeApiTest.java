package com.ozan.exchange.resource.integrationTest;

import com.ozan.exchange.configuration.OzanExchangeConfiguration;
import com.ozan.exchange.dto.Conversion;
import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.util.OzanObjectUtils;
import com.ozan.exchange.web.util.Response;
import com.ozan.exchange.web.util.ResponseError;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { OzanExchangeConfiguration.class } )
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class OzanExchangeApiTest extends AbstractOzanExchangeResourceTest
{

    private static final Logger logger = LoggerFactory.getLogger(OzanExchangeApiTest.class);

    @Test
    public void get_currency_pair_return_exchange_rate_and_200() throws Exception
    {
        String base = "EUR";
        String symbols = "TRY";
        String currency_pair = "EUR,TRY";

        // when
        MvcResult mvcResult = this.mock()
                        .perform(MockMvcRequestBuilders.get("/exchangeApi/exchange")
                                        .param("currency_pair", currency_pair)
                                        .accept("application/json;charset=UTF-8"))
                        .andExpect(status().isOk()).andReturn();

        // then
        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertNotNull(content);

        // return
        OzanExchange ozanExchange = OzanObjectUtils.extractBodyMessage(content, OzanExchange.class);

        Assertions.assertEquals(ozanExchange.getBase(), base);
        Assertions.assertEquals(ozanExchange.getSymbol(), symbols);
        Assertions.assertNotNull(ozanExchange.getRate());

        logger.info(this.getClass().getName() + " - " + ozanExchange.toString());

    }

    @Test
    public void get_currency_pair_illegal_argument_exception() throws Exception
    {
        // when
        String currency_pair = "unknow_pair";

        // then
        MvcResult mvcResult = this.mock()
                        .perform(MockMvcRequestBuilders.get("/exchangeApi/exchange")
                                        .param("currency_pair", currency_pair)
                                        .accept("application/json;charset=UTF-8"))
                        .andExpect(status().isBadRequest()).andReturn();

        // return
        Response response = OzanObjectUtils.extractResponse(
                        mvcResult.getResponse().getContentAsString());

        ResponseError responseError = response.getError();
        Assertions.assertEquals(responseError.getErrorCode().getErrorCode(),
                        ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NOT_ACCEPTABLE.getErrorCode());
    }

    @Test
    public void post_exchange_rate_conversion_valid_input_return_status_200() throws Exception
    {

        // when
        Conversion conversion = Conversion.
                        builder().amount(10.0).base("EUR").symbol("TRY").detail(true).build();

        // then
        MvcResult mvcResult = this.mock()
                        .perform(MockMvcRequestBuilders.post("/exchangeApi/exchange/conversion")
                                        .content(OzanObjectUtils.convertAsString(conversion))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andExpect(jsonPath("$.data.base").value("EUR"))
                        .andExpect(jsonPath("$.data.symbol").value("TRY"))
                        .andExpect(jsonPath("$.data.amount").value(10.0)).andReturn();

        // return
        OzanExchange ozanExchange = OzanObjectUtils.extractBodyMessage(
                        mvcResult.getResponse().getContentAsString(), OzanExchange.class);

        Assertions.assertNotNull(ozanExchange.getConversion());
        Assertions.assertNotNull(ozanExchange.getTransaction());

        logger.info(this.getClass().getName() + " - " + ozanExchange.toString());

    }

    @Test
    public void post_exchange_conversion_invalid_input_expect_no_content() throws Exception
    {

        // when
        Conversion conversion = Conversion.
                        builder().amount(10.0).base("test").symbol("TRY").detail(true).build();

        // then
        MvcResult mvcResult = this.mock()
                        .perform(MockMvcRequestBuilders.post("/exchangeApi/exchange/conversion")
                                        .content(OzanObjectUtils.convertAsString(conversion))
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();
        // return
        Response response = OzanObjectUtils.extractResponse(
                        mvcResult.getResponse().getContentAsString());

        Assertions.assertEquals(response.getError().getErrorCode().getErrorCode(),
                        ErrorCode.EXTERNAL_SERVICE_PROVIDER.EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND
                                        .getErrorCode());
    }

    @Test
    public void get_exchange_rate_valid_input_return_and_200() throws Exception
    {
        // when
        String base = "EUR";
        String symbol = "TRY";
        String amount = "10.0";
        String detail = "true";

        // then
        MvcResult mvcResult = this.mock()
                        .perform(MockMvcRequestBuilders.put("/exchangeApi/exchange/conversion")
                                        .param("base", base).param("symbol", symbol)
                                        .param("amount", amount).param("detail", detail))
                        .andExpect(status().isOk()).andReturn();

        // return
        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertNotNull(content);

        OzanExchange ozanExchange = OzanObjectUtils.extractBodyMessage(content, OzanExchange.class);

        Assertions.assertEquals(ozanExchange.getBase(), base);
        Assertions.assertEquals(ozanExchange.getSymbol(), symbol);
        Assertions.assertNotNull(ozanExchange.getRate());

        logger.info(this.getClass().getName() + " - " + ozanExchange.toString());

    }

    @Test
    public void get_exchange_rate_invalid_input_expect_no_content() throws Exception
    {
        // when
        String base = "test";
        String symbol = "test";
        String amount = "10.0";
        String detail = "true";

        // then
        MvcResult mvcResult = this.mock()
                        .perform(MockMvcRequestBuilders.put("/exchangeApi/exchange/conversion")
                                        .param("base", base).param("symbol", symbol)
                                        .param("amount", amount).param("detail", detail))
                        .andExpect(status().isOk()).andReturn();

        // return
        Response response = OzanObjectUtils.extractResponse(
                        mvcResult.getResponse().getContentAsString());
        Assertions.assertEquals(response.getError().getErrorCode().getErrorCode(),
                        ErrorCode.EXTERNAL_SERVICE_PROVIDER.EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND
                                        .getErrorCode());

        logger.info(this.getClass().getSimpleName() + " - " + response.getError().toString());

    }

}
