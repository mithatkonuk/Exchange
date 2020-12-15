package com.ozan.exchange.web.util;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response
{
    public static final Response EMPTY_RESPONSE = Response.builder().build();
    private Object data;
    private ResponseError error;
}
