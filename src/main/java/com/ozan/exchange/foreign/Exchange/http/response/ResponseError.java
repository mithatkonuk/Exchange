package com.ozan.exchange.foreign.Exchange.http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude( JsonInclude.Include.NON_NULL )
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ResponseError
{
    private int errorCode;
    private String message;
    private String description;
    private String trace;
}
