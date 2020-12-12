package com.ozan.exchange.http.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response
{
    private Object data;
    private ResponseError error;
}
