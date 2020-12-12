package com.ozan.exchange.foreign.Exchange.http.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Response
{
    private Object data;
    private ResponseError error;
}
