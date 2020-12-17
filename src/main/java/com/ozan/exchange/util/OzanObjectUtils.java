package com.ozan.exchange.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozan.exchange.domain.OzanExChangeTransaction;
import com.ozan.exchange.dto.OzanExchange;
import com.ozan.exchange.dto.OzanPaging;
import com.ozan.exchange.web.util.Response;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class OzanObjectUtils
{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convert( Object data, Class<T> tClass )
    {

        return objectMapper.convertValue(data, tClass);
    }

    public static <T> Collection<T> convertCollection( Object data, Class<T> tClass )
    {
        JavaType type = objectMapper.getTypeFactory().
                        constructCollectionType(Collection.class, tClass);

        return objectMapper.convertValue(data, type);
    }

    public static Response extractResponse( String data ) throws JsonProcessingException
    {
        return objectMapper.readValue(data, Response.class);
    }

    public static <T> T extractBodyMessage( String responseAsString, Class<T> tClass )
                    throws JsonProcessingException
    {
        Response response = extractResponse(responseAsString);
        return convert(response.getData(), tClass);
    }

    public static String convertAsString( Object object ) throws JsonProcessingException
    {

        return objectMapper.writeValueAsString(object);
    }

    public static OzanExchange mapper( OzanExChangeTransaction ozanExChangeTransaction )
    {
        return OzanExchange.builder()
                        .transaction(ozanExChangeTransaction.getTransaction().toString())
                        .base(ozanExChangeTransaction.getBase())
                        .symbol(ozanExChangeTransaction.getSymbol())
                        .date(ozanExChangeTransaction.getDateCreated())
                        .amount(ozanExChangeTransaction.getAmount())
                        .rate(ozanExChangeTransaction.getRate())
                        .conversion(ozanExChangeTransaction.getConversion()).build();
    }

    public static OzanPaging mapper( Page<OzanExChangeTransaction> ozanExChangeTransactions )
    {

        List<OzanExchange> ozanExchangeList = ozanExChangeTransactions.getContent().stream()
                        .map(ozanExChangeTransaction -> mapper(ozanExChangeTransaction))
                        .collect(Collectors.toList());

        return OzanPaging.of(ozanExchangeList, ozanExChangeTransactions.getTotalElements(),
                        ozanExChangeTransactions.getPageable().getPageNumber(),
                        ozanExChangeTransactions.getPageable().getPageSize(),
                        ozanExChangeTransactions.getTotalPages());
    }
}
