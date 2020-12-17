package com.ozan.exchange.resource.integrationTest;

import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.dto.OzanPaging;
import com.ozan.exchange.util.OzanObjectUtils;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AbstractOzanExchangeResourceTest
{

    //    @Rule
    //    public JUnitRestDocumentation restDocumentation =
    //                    new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        //                        .apply(documentationConfiguration(this.restDocumentation))
    }

    public MockMvc mock()
    {
        return this.mockMvc;
    }

    public OzanExchange createOzanExchangeTransactionAndReturn( String url, String base,
                    String symbol, String amount, String detail ) throws Exception
    {
        MvcResult mvcResult = this.mock()
                        .perform(MockMvcRequestBuilders.put(url).param("base", base)
                                        .param("symbol", symbol).param("amount", amount)
                                        .param("detail", detail)).andExpect(status().isOk())
                        .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        return OzanObjectUtils.extractBodyMessage(content, OzanExchange.class);
    }

    public OzanExchange getExchangeHistoryByTransaction( String url, String transaction,
                    boolean expectOk ) throws Exception
    {
        ResultActions resultActions = this.mock().perform(MockMvcRequestBuilders.get(url)
                        .param("transaction_id", transaction)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        if(expectOk)
            resultActions.andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();

        return OzanObjectUtils.extractBodyMessage(mvcResult.getResponse().getContentAsString(),
                        OzanExchange.class);
    }

    public OzanPaging getExchangeHistoryByDate( String url, String date, boolean expectOk )
                    throws Exception
    {
        ResultActions resultActions = this.mock()
                        .perform(MockMvcRequestBuilders.get(url).param("created", date)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON));

        if(expectOk)
            resultActions.andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();

        return OzanObjectUtils.extractBodyMessage(mvcResult.getResponse().getContentAsString(),
                        OzanPaging.class);
    }

    public OzanPaging getExchangeHistoryByDateAndTransaction( String url, String transaction,
                    String date, boolean expectOk ) throws Exception
    {
        ResultActions resultActions = this.mock()
                        .perform(MockMvcRequestBuilders.get(url).param("created", date)
                                        .param("transaction_id", transaction)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .accept(MediaType.APPLICATION_JSON));

        if(expectOk)
            resultActions.andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();

        return OzanObjectUtils.extractBodyMessage(mvcResult.getResponse().getContentAsString(),
                        OzanPaging.class);
    }

}
