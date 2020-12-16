package com.ozan.exchange.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ozan.exchange.web.util.Response;

public class ObjectUtils
{
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convert( Object data, Class<T> tClass )
    {

        return objectMapper.convertValue(data, tClass);
    }

    public static <T> T extractResponse( String data, Class<T> tClass )
                    throws JsonProcessingException
    {
        return objectMapper.readValue(data, tClass);
    }

    public static <T> T extractBodyMessage( String responseAsString, Class<T> tClass )
                    throws JsonProcessingException
    {
        Response response = extractResponse(responseAsString, Response.class);
        return convert(response.getData(), tClass);
    }

    public static String convertAsString( Object object ) throws JsonProcessingException
    {

        return objectMapper.writeValueAsString(object);
    }
}
