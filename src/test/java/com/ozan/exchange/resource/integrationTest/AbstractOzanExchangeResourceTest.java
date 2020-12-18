package com.ozan.exchange.resource.integrationTest;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.dto.OzanPaging;
import com.ozan.exchange.util.OzanObjectMapperUtils;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AbstractOzanExchangeResourceTest
{

    private static final ObjectMapper objectMapper = new ObjectMapper();
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
                        .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                        .alwaysDo(MockMvcRestDocumentation.document("{class-name}/{method-name}",
                                        Preprocessors.preprocessRequest(),
                                        Preprocessors.preprocessResponse(
                                                        ResponseModifyingPreprocessors
                                                                        .replaceBinaryContent(),
                                                        ResponseModifyingPreprocessors
                                                                        .limitJsonArrayLength(
                                                                                        objectMapper),
                                                        Preprocessors.prettyPrint())))
                        .apply(MockMvcRestDocumentation
                                        .documentationConfiguration(restDocumentation).uris()
                                        .withScheme("http").withHost("localhost").withPort(8080)
                                        .and().snippets()
                                        .withDefaults(CliDocumentation.curlRequest(),
                                                        HttpDocumentation.httpRequest(),
                                                        HttpDocumentation.httpResponse(),
                                                        AutoDocumentation.requestFields(),
                                                        AutoDocumentation.responseFields(),
                                                        AutoDocumentation.pathParameters(),
                                                        AutoDocumentation.requestParameters(),
                                                        AutoDocumentation.description(),
                                                        AutoDocumentation.methodAndPath(),
                                                        AutoDocumentation.section())).build();

    }

    public MockMvc mock()
    {
        return this.mockMvc;
    }

    public OzanExchange createOzanExchangeTransactionAndReturn( String url, String base,
                    String symbol, String amount, String detail ) throws Exception
    {
        MvcResult mvcResult = this.mock()
                        .perform(MockMvcRequestBuilders.get(url).param("base", base)
                                        .param("symbol", symbol).param("amount", amount)
                                        .param("detail", detail)).andExpect(status().isOk())
                        .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        return OzanObjectMapperUtils.extractBodyMessage(content, OzanExchange.class);
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

        return OzanObjectMapperUtils.extractBodyMessage(mvcResult.getResponse().getContentAsString(),
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

        return OzanObjectMapperUtils.extractBodyMessage(mvcResult.getResponse().getContentAsString(),
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

        return OzanObjectMapperUtils.extractBodyMessage(mvcResult.getResponse().getContentAsString(),
                        OzanPaging.class);
    }

}
