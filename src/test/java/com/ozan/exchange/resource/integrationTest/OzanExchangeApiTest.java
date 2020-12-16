package com.ozan.exchange.resource.integrationTest;

import com.ozan.exchange.configuration.OzanExchangeConfiguration;
import com.ozan.exchange.dto.Conversion;
import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.util.ObjectUtils;
import com.ozan.exchange.web.util.Response;
import com.ozan.exchange.web.util.ResponseError;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { OzanExchangeConfiguration.class } )
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class OzanExchangeApiTest
{

    private static final Logger logger = LoggerFactory.getLogger(OzanExchangeApiTest.class);
    @Rule
    public JUnitRestDocumentation restDocumentation =
                    new JUnitRestDocumentation("target/generated-snippets");
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                        .apply(documentationConfiguration(this.restDocumentation)).build();
    }

    @Test
    public void get_currency_pair_return_exchange_rate_and_200() throws Exception
    {
        String base = "EUR";
        String symbols = "TRY";
        String currency_pair = "EUR,TRY";

        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/exchange-service/exchange")
                                        .param("currency_pair", currency_pair)
                                        .accept("application/json;charset=UTF-8"))
                        .andExpect(status().isOk()).andDo(document("home", requestParameters(
                                        parameterWithName("currency_pair")
                                                        .description("The name to retrieve")),
                                        responseFields(fieldWithPath("data.base")
                                                                        .description("Source Currency"),
                                                        fieldWithPath("data.symbol")
                                                                        .description("Target Currency"),
                                                        fieldWithPath("data.rate")
                                                                        .description("Represent rate between currencies"),
                                                        fieldWithPath("data.date")
                                                                        .description("Represent of Date Transaction event occured"),
                                                        fieldWithPath("error.errorCode")
                                                                        .description("Represent of Error Code"),
                                                        fieldWithPath("error.message")
                                                                        .description("Represent of Custom Error Message"))))
                        .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertNotNull(content);

        OzanExchange ozanExchange = ObjectUtils.extractBodyMessage(content, OzanExchange.class);

        Assertions.assertEquals(ozanExchange.getBase(), base);
        Assertions.assertEquals(ozanExchange.getSymbol(), symbols);
        Assertions.assertNotNull(ozanExchange.getRate());

        logger.info(this.getClass().getName() + " - " + ozanExchange.toString());

    }

    @Test
    public void get_currency_pair_illegal_argument_exception() throws Exception
    {
        String currency_pair = "unknow_pair";
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/exchange-service/exchange")
                                        .param("currency_pair", currency_pair)
                                        .accept("application/json;charset=UTF-8"))
                        .andExpect(status().isBadRequest()).andReturn();

        Response response =
                        ObjectUtils.extractResponse(mvcResult.getResponse().getContentAsString(),
                                        Response.class);

        ResponseError responseError = response.getError();
        Assertions.assertEquals(responseError.getErrorCode(),
                        ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NOT_ACCEPTABLE.getErrorCode());
    }

    @Test
    public void post_exchange_rate_conversion_valid_input_return_status_200() throws Exception
    {
        Conversion conversion = Conversion.
                        builder().amount(10.0).base("EUR").symbol("TRY").detail(true).build();

        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/exchange-service/exchange/conversion")
                                        .content(ObjectUtils.convertAsString(conversion))
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andExpect(jsonPath("$.data.base").value("EUR"))
                        .andExpect(jsonPath("$.data.symbol").value("TRY"))
                        .andExpect(jsonPath("$.data.amount").value(10.0)).andReturn();

        OzanExchange ozanExchange =
                        ObjectUtils.extractBodyMessage(mvcResult.getResponse().getContentAsString(),
                                        OzanExchange.class);

        Assertions.assertNotNull(ozanExchange.getConversion());
        Assertions.assertNotNull(ozanExchange.getTransaction());

        logger.info(this.getClass().getName() + " - " + ozanExchange.toString());

    }

    @Test
    public void post_exchange_conversion_invalid_input_expect_no_content() throws Exception
    {
        Conversion conversion = Conversion.
                        builder().amount(10.0).base("test").symbol("TRY").detail(true).build();

        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/exchange-service/exchange/conversion")
                                        .content(ObjectUtils.convertAsString(conversion))
                                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk()).andReturn();

        Response response =
                        ObjectUtils.extractResponse(mvcResult.getResponse().getContentAsString(),
                                        Response.class);

        Assertions.assertEquals(response.getError().getErrorCode(),
                        ErrorCode.EXTERNAL_SERVICE_PROVIDER.EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND
                                        .getErrorCode());
    }

    @Test
    public void get_exchange_rate_valid_input_return_and_200() throws Exception
    {
        String base = "EUR";
        String symbol = "TRY";
        String amount = "10.0";
        String detail = "true";
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/exchange-service/exchange/conversion")
                                        .param("base", base).param("symbol", symbol)
                                        .param("amount", amount).param("detail", detail))
                        .andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        Assertions.assertNotNull(content);

        OzanExchange ozanExchange = ObjectUtils.extractBodyMessage(content, OzanExchange.class);

        Assertions.assertEquals(ozanExchange.getBase(), base);
        Assertions.assertEquals(ozanExchange.getSymbol(), symbol);
        Assertions.assertNotNull(ozanExchange.getRate());

        logger.info(this.getClass().getName() + " - " + ozanExchange.toString());

    }

    @Test
    public void get_exchange_rate_invalid_input_expect_no_content() throws Exception
    {
        String base = "test";
        String symbol = "test";
        String amount = "10.0";
        String detail = "true";
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/exchange-service/exchange/conversion")
                                        .param("base", base).param("symbol", symbol)
                                        .param("amount", amount).param("detail", detail))
                        .andExpect(status().isOk()).andReturn();

        Response response =
                        ObjectUtils.extractResponse(mvcResult.getResponse().getContentAsString(),
                                        Response.class);
        Assertions.assertEquals(response.getError().getErrorCode(),
                        ErrorCode.EXTERNAL_SERVICE_PROVIDER.EXTERNAL_RESOURCE_EXCHANGE_NOT_FOUND
                                        .getErrorCode());

        logger.info(this.getClass().getSimpleName() + " - " + response.getError().toString());

    }

}
