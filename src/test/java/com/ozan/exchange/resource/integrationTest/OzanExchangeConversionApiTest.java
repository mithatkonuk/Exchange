package com.ozan.exchange.resource.integrationTest;

import com.ozan.exchange.configuration.OzanExchangeConfiguration;
import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.dto.OzanPaging;
import com.ozan.exchange.exception.error.ErrorCode;
import com.ozan.exchange.util.OzanDateUtils;
import com.ozan.exchange.util.OzanObjectUtils;
import com.ozan.exchange.web.util.Response;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringJUnit4ClassRunner.class )
@ContextConfiguration( classes = { OzanExchangeConfiguration.class } )
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class OzanExchangeConversionApiTest extends AbstractOzanExchangeResourceTest
{

    @Test
    public void create_exchange_transaction_return_bydate() throws Exception
    {

        // first create transaction after that fetch it and check
        String base = "EUR";
        String symbol = "TRY";
        String amount = "10.0";
        String detail = "true";
        String url = "/exchangeApi/exchange/conversion";

        OzanExchange ozanExchange =
                        createOzanExchangeTransactionAndReturn(url, base, symbol, amount, detail);

        Assertions.assertNotNull(ozanExchange.getTransaction());

        String conversationUrl = "/conversionApi/conversion/date";
        OzanPaging ozanPaging = getExchangeHistoryByDate(conversationUrl,
                        OzanDateUtils.YYYY_MM_DD.format(OzanDateUtils.nowAsDate()), true);

        Collection<OzanExchange> exchanges =
                        OzanObjectUtils.convertCollection(ozanPaging.getData(), OzanExchange.class);


        for( OzanExchange exchange : exchanges )
        {
            Assertions.assertEquals(OzanDateUtils.YYYY_MM_DD.format(exchange.getDate()),
                            OzanDateUtils.YYYY_MM_DD.format(ozanExchange.getDate()));
        }

    }

    @Test
    public void create_exchange_transaction_return_bytransaction() throws Exception
    {
        // first create transaction after that fetch it and check
        String base = "EUR";
        String symbol = "TRY";
        String amount = "10.0";
        String detail = "true";
        String url = "/exchangeApi/exchange/conversion";

        OzanExchange ozanExchange =
                        createOzanExchangeTransactionAndReturn(url, base, symbol, amount, detail);

        Assertions.assertNotNull(ozanExchange.getTransaction());

        String conversationUrl = "/conversionApi/conversion/transaction";
        OzanExchange exchangeByTransaction = getExchangeHistoryByTransaction(conversationUrl,
                        ozanExchange.getTransaction(), true);

        Assertions.assertEquals(exchangeByTransaction.getTransaction(),
                        ozanExchange.getTransaction());

    }

    @Test
    public void return_illegal_argument_error_code_by_given_invalid_Transaction() throws Exception
    {

        String transaction = "transaction";
        String conversationUrl = "/conversionApi/conversion/transaction";

        MvcResult mvcResult = this.mock().perform(MockMvcRequestBuilders.get(conversationUrl)
                        .param("transaction_id", transaction)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();

        Response response = OzanObjectUtils.extractResponse(
                        mvcResult.getResponse().getContentAsString());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getError().getErrorCode().getCode(),
                        ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_NOT_ACCEPTABLE.getCode());
    }

    @Test
    public void return_illegal_argument_error_code_by_given_invalid_date() throws Exception
    {

        String date = "03-09-2020"; //  not valida date format we already support as a yyyy-MM-dd
        String conversationUrl = "/conversionApi/conversion/date";

        MvcResult mvcResult = this.mock()
                        .perform(MockMvcRequestBuilders.get(conversationUrl).param("created", date)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();

        Response response = OzanObjectUtils.extractResponse(
                        mvcResult.getResponse().getContentAsString());

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response.getError().getErrorCode().getCode(),
                        ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_FORMAT_PROBLEM.getCode());
    }

    @Test
    public void create_exchange_transaction_return_by_transaction_and_date() throws Exception
    {

        // first create transaction after that fetch it and check
        String base = "EUR";
        String symbol = "TRY";
        String amount = "10.0";
        String detail = "true";
        String url = "/exchangeApi/exchange/conversion";

        //when
        OzanExchange ozanExchange =
                        createOzanExchangeTransactionAndReturn(url, base, symbol, amount, detail);

        Assertions.assertNotNull(ozanExchange.getTransaction());

        //then
        String conversationUrl = "/conversionApi/conversion";
        OzanPaging ozanPaging = getExchangeHistoryByDateAndTransaction(conversationUrl,
                        ozanExchange.getTransaction(),
                        OzanDateUtils.YYYY_MM_DD.format(ozanExchange.getDate()), true);

        // return
        Collection<OzanExchange> exchanges =
                        OzanObjectUtils.convertCollection(ozanPaging.getData(), OzanExchange.class);

        Assertions.assertEquals(exchanges.size(), 1);
        for( OzanExchange exchange : exchanges )
        {
            Assertions.assertEquals(OzanDateUtils.YYYY_MM_DD.format(exchange.getDate()),
                            OzanDateUtils.YYYY_MM_DD.format(ozanExchange.getDate()));

            Assertions.assertEquals(exchange.getTransaction(), ozanExchange.getTransaction());
        }

    }

    @Test
    public void return_illegal_argument_error_code_by_given_invalid_transaction() throws Exception
    {

        // first create transaction after that fetch it and check
        String base = "EUR";
        String symbol = "TRY";
        String amount = "10.0";
        String detail = "true";
        String url = "/exchangeApi/exchange/conversion";

        // when
        OzanExchange ozanExchange =
                        createOzanExchangeTransactionAndReturn(url, base, symbol, amount, detail);

        Assertions.assertNotNull(ozanExchange.getTransaction());

        // then
        String conversationUrl = "/conversionApi/conversion";
        MvcResult mvcResult = this.mock().perform(MockMvcRequestBuilders.get(conversationUrl)
                        .param("created", "03-09-1971")
                        .param("transaction_id", ozanExchange.getTransaction())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isBadRequest()).andReturn();

        // return
        Response response = OzanObjectUtils.extractResponse(
                        mvcResult.getResponse().getContentAsString());

        Assertions.assertEquals(
                        ErrorCode.EXCHANGE_SERVICE.ILLEGAL_ARGUMENT_FORMAT_PROBLEM.getCode(),
                        response.getError().getErrorCode().getCode());

    }

}
