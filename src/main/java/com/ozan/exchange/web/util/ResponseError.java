package com.ozan.exchange.web.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ozan.exchange.exception.error.ErrorCode;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude( JsonInclude.Include.NON_NULL )
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ApiModel( description = "Definition of General Response Error" )
public class ResponseError
{
    public static final ResponseError EMPTY_RESPONSE_ERROR =
                    ResponseError.builder().errorCode(ErrorCode.NOT_ERROR).build();

    private ErrorCode errorCode;
}
